package org.acme;

import dev.langchain4j.model.output.structured.Description;

import java.time.LocalDate;

public class TransactionInfo {

    @Description("full name")
    public String name;

    @Description("IBAN value")
    public String iban;

    @Description("Date of the transaction")
    public LocalDate transactionDate;

    @Description("Amount in dollars of the transaction")
    public double amount;

}
