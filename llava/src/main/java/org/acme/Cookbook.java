package org.acme;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface Cookbook {

    Recipe createRecipe(@UserMessage dev.langchain4j.data.message.UserMessage um);
}
