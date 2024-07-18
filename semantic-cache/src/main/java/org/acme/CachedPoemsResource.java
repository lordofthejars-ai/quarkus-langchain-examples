package org.acme;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import io.quarkiverse.langchain4j.redis.RedisEmbeddingStore;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/poem")
public class CachedPoemsResource {

    // Now it is 0.9
    @ConfigProperty(name = "semantic-cache.threshold")
    double threshold;

    @Inject
    RedisEmbeddingStore embeddingStore;

    @Inject
    Assistant assistant;

    // Used for calculating vectors
    @Inject
    EmbeddingModel embeddingModel;

    @Path("/kittens")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String kittens() {

        // Makes request to AI model
        String query1 = "Please write a poem about cute kittens. Maximum 4 lines.";
        String response = assistant.chat(query1);

        System.out.println("********** Output from AI ***********");

        // Creates the vector for the query
        TextSegment segment1 = TextSegment.from(query1);
        Embedding embedding1 = embeddingModel.embed(segment1).content();

        // Stores Content in Redis
        embeddingStore.add(embedding1, TextSegment.from(response));

        // Returns content
        return "AI generated " + System.lineSeparator() + response;
    }

    @Path("/cats")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String cats() {

        // Creates Vector for the query

        String query2 = "Write a poem about cute cats. Maximum 4 lines.";
        TextSegment segment2 = TextSegment.from(query2);
        Embedding embedding2 = embeddingModel.embed(segment2).content();


        // Makes the Query to Redis to check if content is there
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(embedding2)
                .maxResults(1)
                .minScore(threshold)
                .build();

        EmbeddingSearchResult<TextSegment> search = embeddingStore.search(embeddingSearchRequest);
        List<EmbeddingMatch<TextSegment>> matches = search.matches();

        // If not found / or it is under the defined threshold (configured to 0.9)
        if (matches.isEmpty()) {

            // Creates the real query
            System.out.println("********** Output from AI ***********");
            String response = assistant.chat(query2);

            return "AI generated " + System.lineSeparator() + response;

        } else {

            // Gets result from Redis
            System.out.println("********** Cached hit *************");
            String response = matches.getFirst().embedded().text();
            return "Cached generated " + System.lineSeparator() + response;
        }
    }
}
