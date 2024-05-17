import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface ValidateResponse {
    
    @SystemMessage("You are a tester that wants to see if the response is as expected")
    @UserMessage("""
            Validate if the following text between quotes "{input}" has similar meaning of the following text "{expectation}".
            Returns true in case is has similar meaning or false otherwise
            """)
    boolean isSimilar(String input, String expectation);

}
