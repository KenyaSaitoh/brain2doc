package pro.kensait.brain2doc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pro.kensait.brain2doc.exception.OpenAIRetryCountOverException;

public class TestTimeout {

    @Test
    public void testTimeout() {
        String parameter = "" +
                "--src " +
                "D:\\GitHubRepos\\learn_java_basic\\15_enum\\src\\pro\\kensait\\java\\basic\\lsn_15_1_3 " +
                "--resource " +
                "java " +
                "--process " +
                "refactoring " +
                "--dest " +
                "C:/tmp " + 
                "--connectTimeout " +
                "1 " +
                "--timeout " +
                "11 " +
                "--retryCount " +
                "1 " +
                "--retryInterval " +
                "3 " +
                "";
        String[] params = parameter.split(" ");
        Exception exception = assertThrows(
                OpenAIRetryCountOverException.class, () -> Main.main(params));
    }
}