package org.acme.services.ai;

import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.function.Supplier;

@ApplicationScoped
public class WeatherRetrievalAugmentor implements Supplier<RetrievalAugmentor> {

    @Inject
    WeatherContentRetriever weatherContentRetriever;

    @Override
    public RetrievalAugmentor get() {
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(weatherContentRetriever)
                .build();
    }
}
