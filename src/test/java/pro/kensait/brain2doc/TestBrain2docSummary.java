package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestBrain2docSummary {

    @Test
    public void test() {
        String parameter = "" +
                "D:\\GitHubRepos\\brain2doc\\src\\main\\java " +
                "--dest " +
                "C:/tmp/output " +
                "--resource " +
                "java " +
                "--gen " +
                "summary " +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}