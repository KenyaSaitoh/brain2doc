package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestAPITable {

    @Test
    public void testTable() {
        String parameter = "" +
                "D:\\GitHubRepos\\spring_boot2_repo\\spring_rs_person\\src\\main\\java " +
                "--resource " +
                "java " +
                "--gen-table " +
                "RESTサービスAPI " +
                "--fields " +
                "API名、メソッド、操作内容 " +
                "--output-scale " +
                "small " +
                "--dest " +
                "C:/tmp/output " +
                "--auto-split " +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}