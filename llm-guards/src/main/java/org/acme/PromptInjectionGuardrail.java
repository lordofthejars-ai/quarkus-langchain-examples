package org.acme;

import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.jailbreak.JailbreakModel;

@ApplicationScoped
public class PromptInjectionGuardrail implements InputGuardrail {

    @Inject
    JailbreakModel jailbreakModel;

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        final String fullPrompt = userMessage.singleText();
        final String prompt = fullPrompt.substring(fullPrompt.indexOf(':') + 1);

        return jailbreakModel.isSafe(prompt) ? success() : failure("Jailbreak or Prompt Injection");
    }
}
