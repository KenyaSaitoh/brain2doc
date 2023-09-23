package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestNormal {
    @Test
    public void testSimple1() {
        String parameter = "" +
                "--src " +
                "C:\\tmp\\params " +
                "--resource " +
                "java " +
                "--output " +
                "spec " +
                "--outputScale " +
                "medium " + 
                "--dest " +
                "C:/tmp " + 
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
                "--output " +
                "refactoring " +
                "--dest " +
                "C:/tmp " + 
                "--regex " +
                "Cust.* " +
                "--outputScale " +
                "small " + 
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }

}