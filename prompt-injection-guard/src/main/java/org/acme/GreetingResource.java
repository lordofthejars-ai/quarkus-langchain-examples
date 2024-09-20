package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Collections;
import java.util.List;

@Path("/hello")
public class GreetingResource {

    @RestClient
    PromptInjectionService promptInjectionService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        List<PromptInjectionService.Classification> classifications =
                promptInjectionService.isInjection(new PromptInjectionService.PromptMessage("hi"));
        Collections.sort(classifications);

        System.out.println(classifications.getLast());

        return "Hello from Quarkus REST";
    }
}
