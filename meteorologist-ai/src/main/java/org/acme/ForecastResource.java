package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.services.ai.WeatherForecastAi;

@Path("/forecast")
public class ForecastResource {

    @Inject
    WeatherForecastAi weatherForecastAi;

    @GET
    @Path("/{city}")
    @Produces(MediaType.TEXT_PLAIN)
    public String forecast(@PathParam("city") String city) {
        return weatherForecastAi.chat(city);
    }

}
