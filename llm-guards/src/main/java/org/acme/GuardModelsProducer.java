package org.acme;


import ai.djl.MalformedModelException;
import ai.djl.huggingface.translator.TokenClassificationTranslatorFactory;
import ai.djl.inference.Predictor;
import ai.djl.modality.nlp.translator.NamedEntity;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.ModelSupport;
import com.github.tjake.jlama.safetensors.DType;

import com.github.tjake.jlama.util.Downloader;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.acme.huggingface.HuggingFaceDownloader;
import org.acme.huggingface.Model;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class GuardModelsProducer {

    @ConfigProperty(name = "jailbreak.model", defaultValue = "lordofthejars/jailbreak-classifier")
    String jailbreakModel;

    @ConfigProperty(name = "toxic.model", defaultValue = "lordofthejars/toxic-bert")
    String toxicModel;

    @Inject
    HuggingFaceDownloader huggingFaceDownloader;

    static String workingDirectory = System.getProperty("user.home") + "/jlamamodels";
    static String workingDirectoryHF = System.getProperty("user.home") + "/jlamamodels";

    File localJailbreakModelPath;
    File localToxicModelPath;

    ZooModel<String, NamedEntity[]> competitorModel;

    @Inject
    Logger logger;

    @Startup
    public void init() throws IOException, ModelNotFoundException, MalformedModelException {
        this.localJailbreakModelPath = new Downloader(workingDirectory, jailbreakModel).huggingFaceModel();
        this.localToxicModelPath = new Downloader(workingDirectory, toxicModel).huggingFaceModel();

        final Path cloned = huggingFaceDownloader.download(Paths.get(workingDirectoryHF), new Model("lordofthejars", "nuner-competitors"));

        logger.infof("Model cloned at %s", cloned.toAbsolutePath());

        Criteria<String, NamedEntity[]> criteria =
            Criteria.builder()
                .setTypes(String.class, NamedEntity[].class)
                .optModelPath(cloned)
                .optEngine("OnnxRuntime")
                .optTranslatorFactory(new TokenClassificationTranslatorFactory())
                .optProgress(new ProgressBar())
                .build();

        this.competitorModel = criteria.loadModel();
    }

    @Produces
    @Named("jailbreak")
    AbstractModel createJailbreakModel() {
        return ModelSupport.loadClassifierModel(localJailbreakModelPath, DType.F32, DType.I8);
    }

    @Produces
    @Named("toxic")
    AbstractModel createToxicModel() {
        return ModelSupport.loadClassifierModel(localToxicModelPath, DType.F32, DType.I8);
    }

    @Produces
    ZooModel<String, NamedEntity[]> getZooModel() {
        return this.competitorModel;
    }

    @Produces
    @RequestScoped
    public Predictor<String, NamedEntity[]> predictor(ZooModel<String, NamedEntity[]> zooModel) {
        return zooModel.newPredictor();
    }

    void close(@Disposes Predictor<String, NamedEntity[]>  predictor) {
        predictor.close();
    }


}
