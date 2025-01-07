package org.acme.huggingface;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class HuggingFaceDownloader {

    @RestClient
    HubApi hubApi;

    public Path download(Path baseDirectory, Model model) throws IOException {

        Path modelDir = baseDirectory.resolve(model.name());
        if (!Files.exists(modelDir)) {
            Files.createDirectories(modelDir);

            RepoInfo searched = hubApi.search(model.org(), model.name());

            for (Sibling sibling : searched.siblings()) {
                String rfilename = sibling.rfilename();

                File output = modelDir.resolve(rfilename).toFile();
                InputStream inputStream = hubApi.download(model.org(), model.name(), rfilename);

                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(output))) {

                    bufferedInputStream.transferTo(bufferedOutputStream);
                }
            }
        }

        return modelDir;
    }
}
