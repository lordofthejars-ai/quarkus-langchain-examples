package org.acme;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkiverse.langchain4j.runtime.aiservice.GuardrailException;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Singleton
public class GuardrailExceptionMapper implements ExceptionMapper<GuardrailException> {

    @Inject
    ObjectMapper mapper;

    @Override
    public Response toResponse(GuardrailException exception) {

        final ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("reply", exception.getMessage());

        return Response.status(503)
            .entity(objectNode)
            .build();
    }
}
