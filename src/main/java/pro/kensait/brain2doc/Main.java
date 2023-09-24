package pro.kensait.brain2doc;

import pro.kensait.brain2doc.exception.OpenAIRetryCountOverException;
import pro.kensait.brain2doc.params.Parameter;
import pro.kensait.brain2doc.process.Flow;

public class Main {
    public static void main(String[] args) {
        Parameter.setUp(args);
        Parameter param = Parameter.getParameter();
        // TODO System.out.println(param);

        Flow.init(param);
        try {
            Flow.startAndFork();
        } catch(OpenAIRetryCountOverException re) {
            System.err.println("Error occured!!!!!");
            outputReport();
            System.exit(1);
        }
        outputReport();
    }

    private static void outputReport() {
        System.out.println("\n\n########## REPORT ##########");
        for (String report : Flow.getReportList()) {
            System.out.println(report);
        }
    }
}
