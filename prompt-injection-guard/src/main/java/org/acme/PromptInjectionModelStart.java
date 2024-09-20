package org.acme;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PromptInjectionModelStart {

    GenericContainer<?> container;

    void onStart(@Observes StartupEvent ev) {
        List<String> profiles = ConfigUtils.getProfiles();

        if (profiles.contains("dev")) {

            container = new GenericContainer<>(
                    DockerImageName.parse("quay.io/lordofthejars/prompt-guard-inference:1.0.0-model")
            );

            List<String> portBindings = new ArrayList<>();
            portBindings.add("8000:8080");
            container.setPortBindings(portBindings);

            container.start();

        }
    }

    void onStop(@Observes ShutdownEvent ev) {

        List<String> profiles = ConfigUtils.getProfiles();

        if (profiles.contains("dev") && container != null) {
            container.stop();
        }
    }


}
