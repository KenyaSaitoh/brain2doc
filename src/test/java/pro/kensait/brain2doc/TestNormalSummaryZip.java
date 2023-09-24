package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestNormalSummaryZip {
    @Test
    public void testSimple1() {
        String parameter = "" +
                "--src " +
                "E:\\Download\\SpringBootJpa_Web-master.zip " +
                "--resource " +
                "java " +
                "--output " +
                "review " +
                // "summary " +
                "--dest " +
                "C:/tmp " + 
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}