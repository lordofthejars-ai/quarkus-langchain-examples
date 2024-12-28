package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class HuggingFaceDownloader {

    private static final String HUGGING_FACE_URL = "https://huggingface.co/";

    @ConfigProperty(name = "huggingface.directory")
    Optional<String> huggingFaceDirectory;

    public Path clone(String model) throws IOException {
        String baseDirectory = huggingFaceDirectory.orElse(System.getProperty("user.home") + "/huggingface");
        final String modelName = getModelName(model);

        Path modelDir = Paths.get(baseDirectory, modelName);
        Files.createDirectories(modelDir);

        try (Git git = Git.cloneRepository()
                .setDirectory(modelDir.toAbsolutePath().toFile())
            .setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)))
            .setURI(HUGGING_FACE_URL + model).call()) {
            return modelDir;
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    private String getModelName(String model) {
        return model.substring(model.lastIndexOf("/") + 1);
    }

}
