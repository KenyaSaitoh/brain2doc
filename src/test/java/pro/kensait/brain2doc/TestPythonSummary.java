package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestPythonSummary {

    @Test
    public void testPythonSummary() {
        String parameter = "" +
                "--src " +
                "D:\\Java\\brain2doc-src\\python-patterns-master\\patterns\\creational " +
                "--resource " +
                "python " +
                "--gen " +
                "summary " +
                "--dest " +
                "C:/tmp/output " +
            //   "--model " + 
            //    "gpt-3.5-turbo " +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}