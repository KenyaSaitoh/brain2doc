package pro.kensait.brain2doc.transform;

import java.nio.file.Path;
import java.util.List;

public interface TransformStrategy {
    String transform(Path inputFilePath, String requestContent,
            List<String> responseContents, int seqNum);
}
