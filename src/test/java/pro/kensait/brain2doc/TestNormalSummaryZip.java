package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestNormalSummaryZip {
    @Test
    public void testSimple1() {
        String parameter = "" +
                "E:\\Download\\SpringBootJpa_Web-master.zip " +
                "--resource " +
                "java " +
                "--gen " +
                "review " +
                // "summary " +
                "--dest " +
                "C:/tmp/output " + 
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}