package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestTimeout {

    @Test
    public void testTimeout() {
        String parameter = "" +
                "--src " +
                "D:\\GitHubRepos\\learn_java_basic\\15_enum\\src\\pro\\kensait\\java\\basic\\lsn_15_1_3 " +
                "--resource " +
                "java " +
                "--gen " +
                "refactoring " +
                "--dest " +
                "C:/tmp " + 
                "--connect-timeout " +
                "1 " +
                "--timeout " +
                "11 " +
                "--retry-count " +
                "1 " +
                "--retry-interval " +
                "3 " +
                "";
        String[] params = parameter.split(" ");
       // Exception exception = assertThrows(
       //         OpenAIRetryCountOverException.class, () -> Main.main(params));
    }
}