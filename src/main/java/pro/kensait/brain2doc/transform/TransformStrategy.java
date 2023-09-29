package pro.kensait.brain2doc.transform;

import java.nio.file.Path;
import java.util.List;

import pro.kensait.brain2doc.params.GenerateType;
import pro.kensait.brain2doc.params.ResourceType;

public interface TransformStrategy {
    String transform(Path inputFilePath, String requestContent,
            List<String> responseContents, int seqNum);

    public static TransformStrategy getOutputStrategy(ResourceType resourceType,
            GenerateType generateType) {
        if (resourceType == ResourceType.JAVA) {
            return new JavaGeneralTransformStrategy();
        }
        return new OtherTransformStrategy();
    }
}
