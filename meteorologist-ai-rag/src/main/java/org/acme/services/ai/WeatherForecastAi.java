package org.acme.services.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.SessionScoped;

@SessionScoped
@RegisterAiService(retrievalAugmentor = WeatherRetrievalAugmentor.class)
public interface WeatherForecastAi {

    @SystemMessage("""
        You are a meteorologist, and you need to provide a weather forecast 
        explaining how it will be the weather based on the provided data, using maximum of 3 lines.
        
         The weather information is a JSON object and has the following fields:
         
         maxTemperature is the maximum temperature of the day in Celsius degrees  
         minTemperature is the minimum temperature of the day in Celsius degrees 
         precipitation is the amount of water in mm 
         windSpeed is the speed of wind in kilometers per hour 
         weather is the overall weather. 
         
         The forecast should be written in future tense and polite.
         
         The final sentence must boost the sentiment depending on the weather.
         For example, if the weather is sunny you can say something like Enjoy the Sun
         while if it is raining you can say water is necessary for the human life.
    """)
    String chat(@UserMessage String location);

}
