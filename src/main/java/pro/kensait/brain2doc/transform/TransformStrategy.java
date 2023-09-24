package pro.kensait.brain2doc.transform;

import java.nio.file.Path;
import java.util.List;

import pro.kensait.brain2doc.params.OutputType;
import pro.kensait.brain2doc.params.ResourceType;

public interface TransformStrategy {
    String transform(Path inputFilePath, String requestContent,
            List<String> responseContents, int seqNum);

    public static TransformStrategy getOutputStrategy(ResourceType resourceType,
            OutputType outputType) {
        if (resourceType == ResourceType.JAVA) {
            if (outputType == OutputType.SPEC ||
                    outputType == OutputType.SUMMARY ||
                    outputType == OutputType.REVIEW) {
                return new JavaGeneralTransformStrategy();
            }
        }
        return new OtherTransformStrategy();
    }
}
