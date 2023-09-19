package pro.kensait.brain2doc.write;

import java.io.FileWriter;
import java.io.IOException;

import pro.kensait.brain2doc.params.Parameter;

public class MarkdownWriter {

    private static void writeMarkdown(String content) {
        Parameter param = Parameter.getParameter();
        try (FileWriter writer = new FileWriter(param.getDestFilePath().toString(),
                true)) {
           writer.append(content);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}