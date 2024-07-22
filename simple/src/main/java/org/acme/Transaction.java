package org.acme;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface Transaction {

    @UserMessage("Extract information about a transaction from {{it}}")
    TransactionInfo extract(String message);
}
