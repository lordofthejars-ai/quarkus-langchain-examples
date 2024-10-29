package org.acme;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModelFactory;
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
