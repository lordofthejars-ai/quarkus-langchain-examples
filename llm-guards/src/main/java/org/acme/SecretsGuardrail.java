package org.acme;

import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.secrets.SecretsDetector;

@ApplicationScoped
public class SecretsGuardrail implements InputGuardrail {

    @Inject
    SecretsDetector secretsDetector;

    // change to new api to get the input text

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        final String fullPrompt = userMessage.singleText();

        return secretsDetector.isASecretPresent(fullPrompt) ?
            success() : failure("Message with Secrets");
    }

}
