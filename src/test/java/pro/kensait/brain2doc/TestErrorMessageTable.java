package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestErrorMessageTable {

    @Test
    public void testTable() {
        String parameter = "" +
                "D:\\GitHubRepos\\brain2doc\\src\\main\\java " +
                "--resource " +
                "java " +
                "--gen-table " +
                "エラーメッセージ一覧 " +
                "--fields " +
                "例外名、メッセージ名、内容 " +
                "--dest " +
                "C:/tmp/output " +
                "--auto-split" +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}