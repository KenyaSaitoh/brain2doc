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
                "--gen-list " +
                "定数一覧 " +
                "--output-scale " +
                "small " +
                "--dest " +
                "C:/tmp/output " +
                "--auto-split" +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}