package org.acme.services.ai;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.services.geo.GeoCodingService;
import org.acme.services.geo.GeoResults;
import org.acme.services.weather.Daily;
import org.acme.services.weather.WeatherForecast;
import org.acme.services.weather.WeatherForecastService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WeatherContentRetriever implements ContentRetriever  {

    private static String[] PARAMETERS = new String[] {
            "temperature_2m_max",
            "temperature_2m_min",
            "precipitation_sum",
            "wind_speed_10m_max",
            "weather_code"
    };

    @RestClient
    GeoCodingService geoCodingService;

    @RestClient
    WeatherForecastService weatherForecastService;

    @Override
    public List<Content> retrieve(Query query) {

        String city = query.text();
        List<Content> results = new ArrayList<>();

        GeoResults search = geoCodingService.search(city, 1);
        double latitude = search.getFirst().latitude();
        double longitude = search.getFirst().longitude();

        WeatherForecast forecast = weatherForecastService.forecast(latitude, longitude,
                1, PARAMETERS);

        Daily daily = forecast.daily();

        JsonObject json = new JsonObject();
        json.put("maxTemperature", daily.temperature_2m_max()[0]);
        json.put("minTemperature", daily.temperature_2m_min()[0]);
        json.put("precipitation", daily.precipitation_sum()[0]);
        json.put("windSpeed", daily.wind_speed_10m_max()[0]);
        json.put("weather", daily.getWeatherCodes()[0]);

        results.add(Content.from(json.toString()));

        return results;
    }
}
