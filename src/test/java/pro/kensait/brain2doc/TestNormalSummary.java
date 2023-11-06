package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestNormalSummary {
    @Test
    public void testSimple1() {
        String parameter = "" +
                "D:\\src-target\\jackson\\src\\main\\java\\com\\fasterxml\\jackson\\core\\base " +
                "--resource " +
                "java " +
                "--gen " +
                "summary " +
                "--output-scale " +
                "medium " + 
                "--dest " +
                "C:/tmp/output " + 
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}