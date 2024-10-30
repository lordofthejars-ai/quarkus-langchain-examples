package org.acme;

import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.ModelSupport;
import com.github.tjake.jlama.safetensors.DType;
import com.github.tjake.jlama.safetensors.SafeTensorSupport;
import com.github.tjake.jlama.util.Downloader;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GuardModelsProducer {

    @ConfigProperty(name = "jailbreak.model", defaultValue = "lordofthejars/jailbreak-classifier")
    String jailbreakModel;
    static String workingDirectory = System.getProperty("user.home") + "/jlamamodels";

    File localJailbreakModelPath;

    @Startup
    public void init() throws IOException {
        this.localJailbreakModelPath = new Downloader(workingDirectory, jailbreakModel).huggingFaceModel();
    }

    @Produces
    @Named("jailbreak")
    AbstractModel createJailbreakModel() {
        return ModelSupport.loadClassifierModel(localJailbreakModelPath, DType.F32, DType.I8);
    }

}
