package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestLargeEn {

    @Test
    public void testLargeRequest() {
        String parameter = "" +
                "C:\\tmp\\large " +
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