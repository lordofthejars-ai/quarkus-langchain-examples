package org.acme;

import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.invisible.InvisibleCharactersScanner;

@ApplicationScoped
public class InvisibleTextGuardrail implements InputGuardrail {

    @Inject
    InvisibleCharactersScanner invisibleCharactersScanner;

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        final String fullPrompt = userMessage.singleText();
        invisibleCharactersScanner.removeInvisibleCharacters(fullPrompt);

        return success();
    }

}
