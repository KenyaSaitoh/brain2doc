package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestNormalSummary {
    @Test
    public void testSimple1() {
        String parameter = "" +
                "--src " +
                "C:\\tmp\\params " +
                "--resource " +
                "java " +
                "--output " +
                "summary " +
                "--output-scale " +
                "medium " + 
                "--dest " +
                "C:/tmp " + 
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}