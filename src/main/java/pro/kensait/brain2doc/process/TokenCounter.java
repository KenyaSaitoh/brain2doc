package pro.kensait.brain2doc.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class TokenCounter {
    private static final String TOKEN_COUNT_PYTHON_SCRIPT = "python/token_counter_35.py";

    public static Optional<Integer> countTokens(String content) {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        Path tokenCounterFile = null;
        try {
            tokenCounterFile = Path.of(
                classloader.getResource(TOKEN_COUNT_PYTHON_SCRIPT).toURI());
        } catch (URISyntaxException ue) {
            throw new RuntimeException(ue);
        }

        String userDir = System.getProperty("user.dir");
        Path tmpFile = Paths.get(userDir, tokenCounterFile.getFileName().toString());
        try (InputStream in = Files.newInputStream(tokenCounterFile);
                OutputStream out = Files.newOutputStream(tmpFile)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
        } catch (IOException ioe) {
        }

        ProcessBuilder processBuilder = new ProcessBuilder("python",
                tmpFile.toString(),
                content);
        Process process = null;
        try {
            process = processBuilder.start();
            process.waitFor();
        } catch (InterruptedException | IOException ex) {
            return Optional.empty(); // pythonコードが起動できなくても例外は送出しない
        }

        String token = null;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            token = br.readLine();
        } catch (IOException ioe) {
            return Optional.empty(); // pythonコードが起動できなくても例外は送出しない
        }

        if (token == null || token.equals("")) {
            return Optional.empty();
        }

        return Optional.of(Integer.parseInt(token));
    }
}