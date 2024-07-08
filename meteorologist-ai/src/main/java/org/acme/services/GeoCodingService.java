package org.acme.services;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/v1")
public interface GeoCodingService {

    @GET
    @Path("/search")
    GeoResults search(@QueryParam("name") String name, @QueryParam("count") int count);

}
