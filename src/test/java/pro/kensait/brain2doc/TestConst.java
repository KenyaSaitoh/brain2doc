package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestConst {

    @Test
    public void testLargeRequest() {
        String parameter = "" +
                "--src " +
                "D:\\GitHubRepos\\brain2doc\\src\\main\\java " +
                "--resource " +
                "java " +
                "--output " +
                "const " +
                "--outputScale " +
                "small " +
                "--dest " +
                "C:/tmp/output " +
                "--autoSplit" +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}