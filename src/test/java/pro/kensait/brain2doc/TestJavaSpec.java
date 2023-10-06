package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestJavaSpec {

    @Test
    public void testJavaSpec() {
        String parameter = "" +
                "D:\\Java\\brain2doc-src\\jackson-core-2.16\\src\\main\\java\\com\\fasterxml\\jackson\\core\\base " +
                "--resource " +
                "java " +
                "--gen " +
                "spec " +
                "--dest " +
                "C:/tmp/output " +
                "--output-scale " + 
                "small " + 
                "--auto-split " +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}