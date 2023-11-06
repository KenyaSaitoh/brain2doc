package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestConstTable {

    @Test
    public void testTable() {
        String parameter = "" +
                "D:\\src-target\\jackson\\src\\main\\java\\com\\fasterxml\\jackson\\core\\base " +
                "--resource " +
                "java " +
                "--gen-table " +
                "定数一覧 " +
                "--fields " +
                "定数名、内容 " +
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