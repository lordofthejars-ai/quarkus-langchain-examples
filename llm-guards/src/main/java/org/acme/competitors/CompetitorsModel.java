package org.acme.competitors;

import ai.djl.inference.Predictor;
import ai.djl.modality.nlp.translator.NamedEntity;
import ai.djl.translate.TranslateException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Arrays;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class CompetitorsModel {

    @ConfigProperty(name = "guards.competitors")
    List<String> competitors;

    @Inject
    Predictor<String, NamedEntity[]> predictor;

    public boolean isCompetitorCited(String text) {

        final NamedEntity[] predicted;
        try {
            predicted = predictor.predict(text);
        } catch (TranslateException e) {
            return false;
        }

        return Arrays.stream(predicted)
                .filter(p -> p.getScore() > 0.9)
                .map(e -> e.getWord().substring(1))
                .anyMatch(competitors::contains);

    }

}
