package org.acme.huggingface;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestPath;

import java.io.InputStream;

@RegisterRestClient(baseUri = "https://huggingface.co")
public interface HubApi {

    @GET
    @Path("api/models/{org}/{model}")
    RepoInfo search(@RestPath String org, @RestPath String model);

    @GET
    @Path("{org}/{model}/resolve/main/{file}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    InputStream download(@RestPath String org, @RestPath String model, @RestPath String file);

}