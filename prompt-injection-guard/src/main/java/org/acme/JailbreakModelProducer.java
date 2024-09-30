package org.acme;

import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.ModelSupport;
import com.github.tjake.jlama.safetensors.DType;
import com.github.tjake.jlama.safetensors.SafeTensorSupport;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import java.io.File;
import java.io.IOException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class JailbreakModelProducer {

    @ConfigProperty(name = "jailbreak.model", defaultValue = "lordofthejars/jailbreak-classifier")
    String model;

    static String workingDirectory = System.getProperty("java.io.tmpdir") + "/models";


    File localModelPath;

    @Startup
    public void init() throws IOException {
        this.localModelPath = SafeTensorSupport.maybeDownloadModel(workingDirectory, model);
    }

    @Produces
    AbstractModel createModel() {
        return ModelSupport.loadClassifierModel(localModelPath, DType.F32, DType.I8);
    }
}
