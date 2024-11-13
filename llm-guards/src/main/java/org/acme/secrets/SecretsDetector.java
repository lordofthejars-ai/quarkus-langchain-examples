package org.acme.secrets;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@ApplicationScoped
public class SecretsDetector {

    private Map<String, Pattern> secretPatterns = new HashMap<>();

    @Inject
    ObjectMapper mapper;

    @Startup
    public void init() throws IOException {
        try (InputStream is = SecretsDetector.class.getResourceAsStream("/secrets-pattern.json")) {

        }
    }

}
