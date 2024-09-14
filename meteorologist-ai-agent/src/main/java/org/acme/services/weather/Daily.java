package org.acme.services.weather;

import java.util.Arrays;

public record Daily(double[] temperature_2m_max,
                    double[] temperature_2m_min,
                    double[] precipitation_sum,
                    double[] wind_speed_10m_max,
                    int[] weather_code) {

    public WmoCode[] getWeatherCodes() {
        return Arrays.stream(weather_code)
                .mapToObj(WmoCode::translate)
                .toList()
                .toArray(new WmoCode[weather_code.length]);
    }
}
