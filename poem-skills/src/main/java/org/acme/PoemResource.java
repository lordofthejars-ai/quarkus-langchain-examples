package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/poem")
public class PoemResource {

    @Inject
    PoemAiService poemAiService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String poem() {
        return poemAiService.chat(
                "Activate the poem-writing skill and write a poem following its instructions.");
    }
}
