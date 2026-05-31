package org.acme;

import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.skills.SkillsSystemMessageProvider;

@RegisterAiService(systemMessageProviderSupplier = SkillsSystemMessageProvider.class)
public interface PoemAiService {

    String chat(String message);
}