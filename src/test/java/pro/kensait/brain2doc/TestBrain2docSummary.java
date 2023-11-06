package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestBrain2docSummary {

    @Test
    public void test() {
        String parameter = "" +
                "D:\\src-target\\jackson\\src\\main\\java\\com\\fasterxml\\jackson\\core\\base " +
                "--dest " +
                "C:/tmp/output " +
                "--resource " +
                "java " +
                "--gen " +
                "summary " +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}