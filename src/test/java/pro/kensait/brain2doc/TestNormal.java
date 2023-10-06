package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestNormal {
    @Test
    public void testSimple1() {
        String parameter = "" +
                "C:\\tmp\\params " +
                "--resource " +
                "java " +
                "--gen " +
                "spec " +
                "--output-scale " +
                "medium " + 
                "--dest " +
                "C:/tmp/aaa/sdsaaa/tmp2.md " + 
                "--print-prompt" +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }

    @Test
    public void testRegix() {
        String parameter = "" +
                "--src " +
                "C:\\tmp\\params " +
                "--resource " +
                "java " +
                "--gen " +
                "review " +
                "--dest " +
                "C:/tmp " + 
                "--regex " +
                "Cust.* " +
                "--output-scale " +
                "small " + 
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}