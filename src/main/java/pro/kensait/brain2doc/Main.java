package pro.kensait.brain2doc;

import static pro.kensait.brain2doc.common.ConsoleColor.*;

import java.util.List;
import java.util.Locale;

import pro.kensait.brain2doc.config.HelpMessageHolder;
import pro.kensait.brain2doc.exception.OpenAIClientException;
import pro.kensait.brain2doc.exception.OpenAIRetryCountOverException;
import pro.kensait.brain2doc.params.Parameter;
import pro.kensait.brain2doc.process.Flow;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length == 0 ||
                (args.length == 1 && (args[0] == "-help" || args[0] == "--help"))) {
            String lang = Locale.getDefault().getLanguage();
            if (lang != null && ! lang.isEmpty() && ! lang.equals("ja")) {
                printHelpMessage("en");
                return;
            }
            printHelpMessage("ja");
            return;
        }

        Parameter.setUp(args);
        Parameter param = Parameter.getParameter();
        // TODO System.out.println(param);

        Flow.init(param);
        try {
            System.out.println("Starting process flow!");
            Flow.startAndFork();
        } catch(OpenAIClientException | OpenAIRetryCountOverException oe) {
            System.err.println("\n\nError occured!!!!!");
            printReport();
            System.exit(1);
        }
        printReport();
    }

    private static void printHelpMessage(String lang) {
        List<String> messageList = HelpMessageHolder.getInstance().getHelpMessage(lang);
        for (String line : messageList) {
            System.out.println(line
                    .replaceAll("<r>", ANSI_RED)
                    .replaceAll("<b>", ANSI_BLUE)
                    .replaceAll("</>", ANSI_RESET));
        }
    }

    private static void printReport() {
        // TODO
        if (Flow.getReportList().isEmpty()) {
            System.out.println("There is no target resource.\n");
            return;
        }
        System.out.println("\n########## REPORT ##########");
        for (String report : Flow.getReportList()) {
            System.out.println(report);
        }
    }
}
