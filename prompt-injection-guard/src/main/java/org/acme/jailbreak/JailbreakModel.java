package org.acme.jailbreak;

import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.functions.Generator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Map;
import org.jboss.logging.Logger;

@ApplicationScoped
public class JailbreakModel {

    @Named("jailbreak")
    AbstractModel model;

    @Inject
    Logger logger;

    public boolean isSafe(String text) {
        final Map<String, Float> classify = model.classify(text, Generator.PoolingType.MODEL);

        logger.infof("For given prompt: %s has been classified: %s", text, classify);

        return classify.get("benign") > 0.60;
    }

}
