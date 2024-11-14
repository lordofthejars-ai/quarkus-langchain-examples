package org.acme.secrets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.regex.Pattern;

@ApplicationScoped
public class SecretsDetector {

    private Map<String, Pattern> secretPatterns = new HashMap<>();

    @ConfigProperty(name = "enabled.secrets", defaultValue = "Generic-secret")
    List<String> secrets;

    @Inject
    ObjectMapper mapper;

    @Startup
    public void init() throws IOException {
        try (InputStream is = SecretsDetector.class.getResourceAsStream("/secrets-pattern.json")) {
            JsonNode rootNode = mapper.readTree(is);
            rootNode.elements().forEachRemaining(
                    node -> {
                        secretPatterns.put(
                                node.get("name").asText(),
                                Pattern.compile(node.get("pattern").asText())
                        );
                    }
            );
        }
    }

    public boolean isASecretPresent(String text) {

        List<Pattern> enabledSecretsPatterns = secrets
                .stream()
                .filter(s -> secretPatterns.containsKey(s))
                .map(s -> secretPatterns.get(s))
                .toList();

        for (Pattern p : enabledSecretsPatterns) {
            if (p.matcher(text).find()) {
                return true;
            }
        }

        return false;
    }

}
