package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestBrain2docSpec {

    @Test
    public void test() {
        String parameter = "" +
                "D:\\GitHubRepos\\brain2doc\\src\\main\\java " +
                "--resource " +
                "java " +
                "--gen " +
                "spec " +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}