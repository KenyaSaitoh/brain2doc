package pro.kensait.brain2doc.params;

import pro.kensait.brain2doc.walk_read.PromptAttacher;

public class ParamMain {
    public static void main(String[] args) {
        String parameter = "" +
                "--stopOnFailure " + 
                "--src " +
                "C:\\Users\\kenya\\AppData\\Local\\Programs\\Python\\Python311\\Lib " +
                "--resource " +
                "java " +
                "--process " +
                "spec " +
                "--scale " +
                "medium" + 
                "";
        String[] params = parameter.split(" ");
        Parameter.setUp(params);
        Parameter param = Parameter.getParameter();
        System.out.println(param);

        PromptAttacher.attachPrompt(null);
    }
}
