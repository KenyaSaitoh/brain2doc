package pro.kensait.brain2doc.token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Optional;

public class TokenCounter {
    private static final String TOKEN_COUNT_PYTHON_SCRIPT = "python/token_counter.py";

    public static void main(String[] args) throws Exception {
        countTokens("fdfs dfsdfsd  fdfds");
    }

    public static Optional<Integer> countTokens(String text) throws Exception {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        Path tokensCounterFile = Path.of(
                classloader.getResource(TOKEN_COUNT_PYTHON_SCRIPT).toURI());
        String absolutePath = tokensCounterFile.toAbsolutePath().toString();

        ProcessBuilder processBuilder = new ProcessBuilder("python", absolutePath, text);
        Process process = processBuilder.start();

        String token = null;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            token = br.readLine();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        if (token == null || token.equals("")) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(token));
    }
}