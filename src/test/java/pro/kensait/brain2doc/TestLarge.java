package pro.kensait.brain2doc;

import org.junit.jupiter.api.Test;

public class TestLarge {

    @Test
    public void testLargeRequest() {
        String parameter = "" +
                "D:\\GitHubRepos\\udemy_projects\\learn_java_testing\\junit5\\src\\main\\java " +
                "--resource " +
                "java " +
                "--gen " +
                "writing " +
                "--dest " +
                "D:\\LetsLearn\\OpenAITest\\output\\junit-src-explanation.md " +
                "--template " +
                "D:\\LetsLearn\\OpenAITest\\template_writing.yaml " +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }

    @Test
    public void testLargeRequest2() {
        String parameter = "" +
                "C:\\tmp\\large2 " +
                "--resource " +
                "java " +
                "--gen " +
                "review " +
                "--model " +
                "gpt-3.5-turbo" +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}