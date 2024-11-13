package org.acme.toxicity;

import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.functions.Generator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Map;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ToxicModel {

    @Named("toxic")
    AbstractModel model;

    @ConfigProperty(name = "toxic.guard.threshold", defaultValue = "0.8")
    float threshold;

    @Inject
    Logger logger;

    public boolean isToxic(String text) {
        final Map<String, Float> classify = model.classify(text, Generator.PoolingType.MODEL);

        logger.infof("For given prompt: %s has been classified: %s", text, classify);

        final Optional<Map.Entry<String, Float>> first = classify
            .entrySet()
            .stream()
            .filter(e -> e.getValue() >= this.threshold)
            .findFirst();

        return first.map(e -> {
            logger.infof("Toxic text categorized as %s", e.getKey());
            return true;
        }).orElse(false);

    }
}
