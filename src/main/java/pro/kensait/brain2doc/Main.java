package pro.kensait.brain2doc;

import static pro.kensait.brain2doc.common.Const.*;

import java.util.List;
import java.util.Locale;

import pro.kensait.brain2doc.config.HelpMessageHolder;
import pro.kensait.brain2doc.exception.OpenAIClientException;
import pro.kensait.brain2doc.exception.OpenAIInsufficientQuotaException;
import pro.kensait.brain2doc.exception.OpenAIInvalidAPIKeyException;
import pro.kensait.brain2doc.exception.OpenAIRateLimitExceededException;
import pro.kensait.brain2doc.exception.RetryCountOverException;
import pro.kensait.brain2doc.params.Parameter;
import pro.kensait.brain2doc.process.Flow;

public class Main {
    private static final String JAPANESE = "ja";
    private static final String ENGLISH = "en";
    private static final String REPORT_HEADING = "### REPORT";

    public static void main(String[] args) {
        if (args == null || args.length == 0 ||
                (args.length == 1 && (args[0] == "-help" || args[0] == "--help"))) {
            String lang = Locale.getDefault().getLanguage();
            if (lang != null && ! lang.isEmpty() && ! lang.equals(JAPANESE)) {
                printHelpMessage(ENGLISH);
                return;
            }
            printHelpMessage(JAPANESE);
            return;
        }

        Parameter.setUp(args);
        Parameter param = Parameter.getParameter();
        // TODO System.out.println(param);

        Flow.init(param);
        try {
            printBanner();
            Flow.startAndFork();
        } catch(OpenAIInvalidAPIKeyException oe) {
            System.err.println(LINE_SEP + "OpenAI API Key is Invalid occured!!!!!");
            printReport();
            System.exit(1);
        } catch(OpenAIInsufficientQuotaException oe) {
            System.err.println(LINE_SEP + "OpenAI Quota is Insufficient!!!!!");
            printReport();
            System.exit(1);
        } catch(OpenAIRateLimitExceededException oe) {
            System.err.println(LINE_SEP + "OpenAI Rate Limit Exceeded!!!!!");
            System.out.println(oe.getClientErrorBody());
            printReport();
            System.exit(1);
        } catch(OpenAIClientException oe) {
            System.err.println(LINE_SEP + "OpenAI ClientError occured!!!!!");
            printReport();
            System.exit(1);
        } catch (RetryCountOverException oe) {
            System.err.println(LINE_SEP + "Retry Count is Over!!!!!");
            printReport();
            System.exit(1);
        }
        printReport();
        System.exit(0);
    }

    private static void printHelpMessage(String lang) {
        List<String> messageList = HelpMessageHolder.getInstance().getHelpMessage(lang);
        for (String line : messageList) {
            System.out.println(line);
        }
    }

    private static void printBanner() {
        System.out.println("##############################");
        System.out.println("#                            #");
        System.out.println("#    Welcome to Brain2doc    #");
        System.out.println("#                            #");
        System.out.println("##############################");
        System.out.println("");
    }
    
    private static void printReport() {
        // TODO
        if (Flow.getReportList().isEmpty()) {
            System.out.println("There is no target resource." + LINE_SEP);
            return;
        }
        System.out.println(LINE_SEP + REPORT_HEADING + LINE_SEP);
        for (String report : Flow.getReportList()) {
            System.out.println(report);
        }
    }
}
