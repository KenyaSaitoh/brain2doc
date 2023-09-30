package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestOuterTemplate {

    @Test
    public void testLargeRequest() {
        String parameter = "" +
                "--src " +
                "D:\\GitHubRepos\\brain2doc\\src\\main\\ " +
                "--resource " +
                "java " +
                "--gen-list " +
                "定数一覧 " +
                "--template " +
                "D:\\GitHubRepos\\brain2doc\\template_ja.yaml " +
                "--dest " +
                "C:/tmp/output " +
                "--auto-split" +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}