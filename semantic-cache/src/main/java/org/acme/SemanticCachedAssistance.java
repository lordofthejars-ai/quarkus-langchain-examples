package org.acme;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import io.quarkiverse.langchain4j.redis.RedisEmbeddingStore;
import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@Priority(10)
@Decorator
public class SemanticCachedAssistance implements Assistant {

    @Inject
    RedisEmbeddingStore embeddingStore;

    // Used for calculating vectors
    @Inject
    EmbeddingModel embeddingModel;

    @Inject
    @Any
    @Delegate
    Assistant assistant;

    @Override
    public String chat(String message) {

        final Embedding embedding = getEmbedding(message);
        final List<EmbeddingMatch<TextSegment>> matches =
            findContentInRedis(embedding);

        if (matches.isEmpty()) {

            logger.info("Cache misses, generating output");
            String response = assistant.chat(message);

            // Stores Content in Redis
            embeddingStore.add(embedding, TextSegment.from(response));
            return response;
        }

        logger.info("Cache hit, returning previous value");
        return matches.getFirst().embedded().text();
    }

    private List<EmbeddingMatch<TextSegment>> findContentInRedis(Embedding embedding) {
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
            .queryEmbedding(embedding)
            .maxResults(1)
            .minScore(threshold)
            .build();

        EmbeddingSearchResult<TextSegment> search = embeddingStore.search(embeddingSearchRequest);
        return search.matches();
    }

    private Embedding getEmbedding(String message) {
        TextSegment segment = TextSegment.from(message);
        return embeddingModel.embed(segment).content();
    }

    @Inject
    Logger logger;

    // Now it is 0.9
    @ConfigProperty(name = "semantic-cache.threshold")
    double threshold;
}
