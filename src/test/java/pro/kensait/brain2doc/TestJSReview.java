package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestJSReview {

    @Test
    public void testJSReview() {
        String parameter = "" +
                "--src " +
                "D:\\Java\\brain2doc-src\\realworld-main\\apps\\api\\src\\app\\controllers " +
                "--resource " +
                "js " +
                "--gen " +
                "review " +
                "--output-scale " +
                "small " +
                "--dest " +
                "C:/tmp/output " +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}