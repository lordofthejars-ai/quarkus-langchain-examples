import org.assertj.core.api.AbstractAssert;

import io.quarkus.arc.Arc;

public class AiAssert extends AbstractAssert<AiAssert, String> {

    ValidateResponse validateResponse;
    
    protected AiAssert(String actual) {
        super(actual, AiAssert.class);

        // Injects AI Service from LangChain4J
        validateResponse = Arc.container().instance(ValidateResponse.class).get();
    }

    public static AiAssert assertThat(String actual) {
        return new AiAssert(actual);
    }

    public AiAssert isSimilarTo(String expect) {
        // check that actual we want to make assertions on is not null.
        isNotNull();
    
        // check condition

        if (!validateResponse.isSimilar(actual, expect)) {
          failWithMessage("Expected meaning to be '<%s>' for '%s' be similar but was not", expect, actual);
        }
    
        // return the current assertion for method chaining
        return this;
      }
    
}
