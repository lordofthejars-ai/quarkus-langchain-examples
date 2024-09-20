package org.acme;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Path("/")
@RegisterRestClient(configKey = "prompt-injection")
public interface PromptInjectionService {

    record Classification(String label, double score) implements Comparable<Classification>{
        @Override
        public int compareTo(@NotNull PromptInjectionService.Classification o) {
            return Double.compare(this.score, o.score);
        }
    }

    record PromptMessage(String prompt) {
    }


    @POST
    List<Classification> isInjection(PromptMessage promptMessage);

}
