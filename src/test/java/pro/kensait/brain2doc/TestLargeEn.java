package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestLargeEn {

    @Test
    public void testLargeRequest() {
        String parameter = "" +
                "D:\\src-target\\jackson\\src\\main\\java\\com\\fasterxml\\jackson\\core\\base " +
                "--resource " +
                "java " +
                "--gen " +
                "spec " +
                "--output-scale " +
                "small " +
                "--dest " +
                "C:/tmp/output " +
                "--lang " + 
                "ja " +
                "--auto-split" +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}