package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestLargeEn {

    @Test
    public void testLargeRequest() {
        String parameter = "" +
                "--src " +
                "C:\\tmp\\large " +
                "--resource " +
                "java " +
                "--output " +
                "spec " +
                "--output-scale " +
                "small " +
                "--dest " +
                "C:/tmp/output " +
                "--lang " + 
                "en " +
                "--auto-split" +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}