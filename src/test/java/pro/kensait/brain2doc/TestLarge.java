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
                "--output " +
                "review " +
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