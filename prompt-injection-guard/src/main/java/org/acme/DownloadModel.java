package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CountingInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@ApplicationScoped
public class DownloadModel {

    public static final int ONE_MEGABYTE = 1_000_000;
    @Inject
    ExecutorService executorService;

    public Path downloadModel() throws IOException {
        return download(URI.create("https://huggingface.co/protectai/fmops-distilbert-prompt-injection-onnx/resolve/main/model.onnx").toURL());
    }

    public Path downloadTokenizer() throws IOException {
        return download(URI.create("https://huggingface.co/protectai/fmops-distilbert-prompt-injection-onnx/resolve/main/tokenizer.json").toURL());
    }

    protected Path download(URL url) throws IOException {

        final String path = url.getPath();
        String filename = path.substring(path.lastIndexOf('/') + 1);

        final String tmpdir = System.getProperty("java.io.tmpdir");
        Path destination = Paths.get(tmpdir, filename);

        if (! Files.exists(destination)) {
            download(url, filename, destination);
        }

        return destination;

    }

    protected void download(URL url, String outputFilename, Path saveAt) throws IOException {


        final HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
        long completeFileSize = httpConnection.getContentLength();

        try(InputStream inputStream = url.openStream();
            CountingInputStream cis = new CountingInputStream(inputStream);
            FileOutputStream fileOS = new FileOutputStream(saveAt.toAbsolutePath().toString());
            ProgressBar pb = new ProgressBar(outputFilename, Math.floorDiv(completeFileSize, ONE_MEGABYTE))) {

                pb.setExtraMessage("Downloading...");

                executorService.execute(() -> {
                    try {
                        IOUtils.copyLarge(cis, fileOS);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });


                while (cis.getByteCount() < completeFileSize) {
                    pb.stepTo(Math.floorDiv(cis.getByteCount(), ONE_MEGABYTE));
                }

                pb.stepTo(Math.floorDiv(cis.getByteCount(), ONE_MEGABYTE));
                System.out.println("File downloaded successfully!");
            }
        }

}
