package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestJavaSpec {

    @Test
    public void testJavaSpec() {
        String parameter = "" +
                "--src " +
                "D:\\Java\\brain2doc-src\\jackson-core-2.16\\src\\main\\java\\com\\fasterxml\\jackson\\core\\base " +
                "--resource " +
                "java " +
                "--gen " +
                "spec " +
                "--dest " +
                "C:/tmp/output " +
                "--auto-split" +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}