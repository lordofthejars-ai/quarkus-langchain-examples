package org.acme.services.weather;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "openmeteo")
@Path("/v1")
public interface WeatherForecastService {

    @GET
    @Path("/forecast")
    WeatherForecast forecast(@QueryParam("latitude") double latitude,
                             @QueryParam("longitude") double longitude,
                             @QueryParam("forecast_days") int forecastDays,
                             @QueryParam("daily") String[] parametersValues
    );

}
