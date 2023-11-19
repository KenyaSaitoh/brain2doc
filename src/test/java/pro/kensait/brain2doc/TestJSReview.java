package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestJSReview {

    @Test
    public void testJSReview() {
        String parameter = "" +
                "D:\\src-target\\realworld-main\\apps\\api\\src\\app\\controllers " +
                "--resource " +
                "js " +
                "--gen " +
                "review " +
                "--output-scale " +
                "small " +
                "--dest " +
                "C:/tmp/output " +
                "--temparature " +
                "0.0F " +
                "--model " +
                "gpt-4-1106-preview " +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}