package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.services.GeoCodingService;
import org.acme.services.GeoResults;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/hello")
public class GreetingResource {

    @RestClient
    GeoCodingService geoCodingService;

    @GET
    public GeoResults hello() {
        return geoCodingService.search("Berlin, Germany", 1);
    }
}
