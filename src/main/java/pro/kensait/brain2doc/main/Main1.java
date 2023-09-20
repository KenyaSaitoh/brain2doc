package pro.kensait.brain2doc.main;

import pro.kensait.brain2doc.params.Parameter;
import pro.kensait.brain2doc.process.ProcessFlow;

public class Main1 {
    public static void main(String[] args) {
        String parameter = "" +
                "--stopOnFailure " + 
                "--src " +
                "D:\\GitHubRepos\\learn_java_basic\\15_enum\\src\\pro\\kensait\\java\\basic\\lsn_15_1_3 " +
                "--resource " +
                "java " +
                "--process " +
                "refactoring " +
                "--scale " +
                "medium " + 
                "--dest " +
                "C:/tmp " + 
                "--regex " +
                "Cust.*" + 
                "";
        String[] params = parameter.split(" ");
        System.out.println(params);
        Parameter.setUp(params);
        Parameter param = Parameter.getParameter();
        System.out.println(param);

        ProcessFlow.inputProcess(param);
    }
}
