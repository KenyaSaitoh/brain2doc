package pro.kensait.brain2doc.walk_read;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import pro.kensait.brain2doc.openai.ApiClient;
import pro.kensait.brain2doc.params.ResourceType;

public class FileProcessor {

    public static void processFile(Path inputFilePath, ResourceType resourceType) {
        try {
            if (resourceType.matchesExt(inputFilePath.toString())) {
                List<String> inputFileLines = Files.readAllLines(inputFilePath);
                askToOpenAPI(inputFileLines);
            }
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static void askToOpenAPI(List<String> inputFileLines) {
        List<String> requestLines = PromptAttacher.attachPrompt(inputFileLines);
        ApiClient.ask(requestLines);
    }
}