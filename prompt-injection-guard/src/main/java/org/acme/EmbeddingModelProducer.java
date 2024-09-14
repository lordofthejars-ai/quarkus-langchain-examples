package org.acme;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.ndarray.NDList;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;

@ApplicationScoped
public class EmbeddingModelProducer {

    @Inject
    DownloadModel downloadModel;

    @Startup
    public void createPromptInjectionEmbedding()
        throws IOException, ModelNotFoundException, MalformedModelException {
        Path pathToModel = downloadModel.downloadModel();
        Path pathToTokenizer = downloadModel.downloadTokenizer();


        OrtEnvironment env = OrtEnvironment.getEnvironment();
        OrtSession.SessionOptions sessionOptions = new OrtSession.SessionOptions();
        OrtSession session = env.createSession(onnxModelPath, sessionOptions);
    }

    /**private ZooModel<String, Boolean> model;

    @Startup
    public void createPromptInjectionEmbedding()
        throws IOException, ModelNotFoundException, MalformedModelException {
        Path pathToModel = downloadModel.downloadModel();
        Path pathToTokenizer = downloadModel.downloadTokenizer();
        Criteria<String, Boolean> criteria =
             Criteria.builder()
                 .setTypes(String.class, Boolean.class)
                .optTranslator(new PromptInjectionTranslator())

                .optModelPath(pathToModel)
                .optEngine("OnnxRuntime")
                .build();

        this.model = criteria.loadModel();
    }

    @Produces
    ZooModel<String, Boolean> getZooModel() {
        return this.model;
    }

    @Produces
    @RequestScoped
    public Predictor<String, Boolean> predictor(ZooModel<String, Boolean> zooModel) {
        return zooModel.newPredictor();
    }

    void close(@Disposes Predictor<String, Boolean>  predictor) {
        System.out.println("Closes Predictor");
        predictor.close();
    }**/

}
