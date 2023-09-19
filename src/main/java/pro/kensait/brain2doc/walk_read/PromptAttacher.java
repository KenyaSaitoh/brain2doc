package pro.kensait.brain2doc.walk_read;

import java.util.List;
import java.util.Map;

import pro.kensait.brain2doc.config.PromptHolder;
import pro.kensait.brain2doc.params.Parameter;
import pro.kensait.brain2doc.params.ProcessType;
import pro.kensait.brain2doc.params.ResourceType;

public class PromptAttacher {
    @SuppressWarnings("rawtypes")
    public static List<String> attachPrompt(List<String> inputFileLines) {
        Parameter param = Parameter.getParameter();
        ResourceType resourceType = param.getResourceType();
        ProcessType processType = param.getProcessType();
        Map map = PromptHolder.getMap();
        Map resourceMap = (Map) (map.get(resourceType.getName()));
        Map messageMap = (Map) resourceMap.get("processes");
        String headerMessages = (String) messageMap.get(processType.getName());
        System.out.println(headerMessages);
        return null;
    }
}
