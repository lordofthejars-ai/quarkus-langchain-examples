package org.acme;

import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailParams;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.jailbreak.JailbreakModel;

@ApplicationScoped
public class PromptInjectionGuardrail implements InputGuardrail {

    @Inject
    JailbreakModel jailbreakModel;

    // change to new api to get the input text

    @Override
    public InputGuardrailResult validate(InputGuardrailParams inputGuardrailParams) {
        String prompt = (String) inputGuardrailParams.variables().get("story");

        System.out.println(prompt);

        return jailbreakModel.isSafe(prompt) ? success() : failure("Jailbreak or Prompt Injection");
    }
}
