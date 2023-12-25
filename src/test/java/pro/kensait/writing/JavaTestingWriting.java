package pro.kensait.writing;

import org.junit.jupiter.api.Test;

import pro.kensait.brain2doc.Main;

public class JavaTestingWriting {

    @Test
    public void execute() {
        String parameter = "" +
                "D:/GitHubRepos/udemy_projects/learn_java_testing/junit5" +
                "/src/main/java/pro/kensait/java/calc " +
                "--resource " +
                "java " +
                "--gen " +
                "spec " +
                "--dest " +
                "D:/LetsLearn/OpenAITest/output/junit-src-explanation.md " +
                "--template " +
                "D:/LetsLearn/OpenAITest/template_writing.yaml " +
                "";
        String[] params = parameter.split(" ");
        Main.main(params);
    }
}