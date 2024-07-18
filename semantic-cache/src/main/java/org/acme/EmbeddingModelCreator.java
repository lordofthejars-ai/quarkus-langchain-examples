package org.acme;

import dev.langchain4j.model.embedding.AllMiniLmL6V2QuantizedEmbeddingModelFactory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class EmbeddingModelCreator {

    @Produces
    public EmbeddingModel create() {
        AllMiniLmL6V2QuantizedEmbeddingModelFactory f = new AllMiniLmL6V2QuantizedEmbeddingModelFactory();
        return f.create();
    }
}
