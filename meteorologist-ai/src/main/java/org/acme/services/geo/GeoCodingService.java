package org.acme.services.geo;


import io.quarkus.cache.CacheResult;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/v1")
public interface GeoCodingService {

    @GET
    @Path("/search")
    @CacheResult(cacheName = "geo-results")
    GeoResults search(@QueryParam("name") String name, @QueryParam("count") int count);

}
