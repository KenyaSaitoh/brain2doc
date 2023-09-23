package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestLarge {

    @Test
    public void testLargeRequest() {
        String parameter = "" +
                "--src " +
                "C:\\tmp\\large " +
                "--resource " +
                "java " +
                "--output " +
                "refactoring " +
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