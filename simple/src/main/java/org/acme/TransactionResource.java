package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/transaction")
public class TransactionResource {

    @Inject
    Transaction assistant;

    @GET
    public TransactionInfo process() {

        String text = "My name is Alex, I did a transaction on July 4th 2023 from my account with IBAN 123456789 of $25.5";
        return assistant.extract(text);
    }
}
