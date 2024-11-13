package org.acme;

import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.acme.toxicity.ToxicModel;

@ApplicationScoped
public class ToxicityGuardrail implements InputGuardrail {

    @Inject
    ToxicModel toxicModel;

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        final String fullPrompt = userMessage.singleText();
        final String prompt = fullPrompt.substring(fullPrompt.indexOf(':') + 1);

        return toxicModel.isToxic(prompt) ? success() : failure("Toxic Message");
    }

}
