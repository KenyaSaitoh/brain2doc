package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestLarge {

    @Test
    public void testLargeRequest() {
        String parameter = "" +
                "--src " +
                "C:\\tmp\\large2 " +
                "--resource " +
                "java " +
                "--gen " +
                "review " +
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