package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import org.jboss.logging.Logger;

@Path("/write")
public class PoemResource {

    @Inject
    Assistant assistant;

    @Path("/poem")
    public void writePoem() {

        String query1 = "Please write a poem about cute kittens. Maximum 4 lines.";
        logger.info(assistant.chat(query1));

        String query2 = "Write a poem about cute cats. Maximum 4 lines.";
        logger.info(assistant.chat(query2));
        
    }

    @Inject
    Logger logger;

}
