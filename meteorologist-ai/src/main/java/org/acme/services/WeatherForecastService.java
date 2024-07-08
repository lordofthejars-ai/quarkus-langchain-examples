package org.acme.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/v1")
public interface WeatherForecastService {

    @GET
    @Path("/forecast")
    WeatherForecast forecast();

}
