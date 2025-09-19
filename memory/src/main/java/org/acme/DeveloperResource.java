package org.acme;



import dev.langchain4j.model.chat.ChatModel;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import dev.langchain4j.chain.ConversationalChain;


@Path("/code")
public class DeveloperResource {

    @Inject
    private ChatModel model;


    @GET
    @Path("/k8s")
    @Produces(MediaType.TEXT_PLAIN)
    public void generateKubernetes() {

        ConversationalChain chain = ConversationalChain.builder()
                .chatModel(model)
                .build();

        String userMessage1 = "Can you give a brief explanation of Kubernetes, 3 lines max?";
        System.out.println("[User]: " + userMessage1 + System.lineSeparator());

        String answer1 = chain.execute(userMessage1);
        System.out.println("[LLM]: " + answer1 + System.lineSeparator());

        String userMessage2 = "Can you give me a YAML example to deploy an application for that?";
        System.out.println("[User]: " + userMessage2 + System.lineSeparator());

        String answer2 = chain.execute(userMessage2);
        System.out.println("[LLM]: " + answer2);

    }

    @Inject
    Assistant assistant;

    @GET
    @Path("/guess")
    @Produces(MediaType.TEXT_PLAIN)
    public void guessWho() {

        System.out.println(assistant.chat(1, "Hello, my name is Klaus"));

        System.out.println(assistant.chat(2, "Hello, my name is Francine"));

        System.out.println(assistant.chat(1, "What is my name?"));

        System.out.println(assistant.chat(2, "What is my name?"));

    }
}
