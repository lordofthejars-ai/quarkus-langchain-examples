package org.acme;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;

import dev.langchain4j.model.output.Response;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    private ChatLanguageModel model;

    @Inject
    Cookbook cookbook;

    @GET
    public Recipe hello() {

        UserMessage userMessage = UserMessage.from(
                TextContent.from("""
                    Write me a recipe for cooking with the ingredients present in the given fridge photograph
                    
                    The output must be in JSON format, with a field named title, 
                    a field named ingredients being an array with all ingredients, 
                    and a field named steps, as an array of all the steps to follow to cook the dish.
                    
                    Only return the JSON document not embedded inside any .
                """),
                ImageContent.from("https://hips.hearstapps.com/hmg-prod/images/refrigerator-full-of-food-royalty-free-image-1596641208.jpg"));

        Recipe r = cookbook.createRecipe(userMessage);

        return r;
    }
}
