package org.acme;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.guardrails.InputGuardrails;

@RegisterAiService
public interface StoryService {

    @UserMessage("""
        Write a story about the following: {story}
        """)
    @InputGuardrails(PromptInjectionGuardrail.class)
    String writeStory(String story);

}
