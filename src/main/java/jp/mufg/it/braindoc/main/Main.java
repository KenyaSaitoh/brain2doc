package jp.mufg.it.braindoc.main;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.mufg.it.braindoc.apiclient.Message;
import jp.mufg.it.braindoc.apiclient.RequestBody;
import jp.mufg.it.braindoc.apiclient.ResponseBody;

public class Main {
    private static final String PACKAGE_REGEX = "package (.*);";
    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPENAI_API_KEY = "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";
    private static final String MODEL = "gpt-3.5-turbo";
    private static final Path srcPath = Paths.get("D:\\GitHubRepos\\gpt-doc-creator\\src\\main\\java");
    // private static final Path srcPath = Paths.get("D:\\GitHubRepos\\spring_boot2_repo\\jquery_person\\src");
    // private static final Path srcPath = Paths.get("D:\\GitHubRepos\\aws_eks_repo\\spring_mvc_person_mybatis\\src\\main\\resources\\templates");
    // private static final Path srcPath = Paths.get("D:\\GitHubRepos\\aws_eks_repo\\spring_mvc_person_mybatis\\sql");

    private static final Path destPath = Paths.get("D:\\GitHubRepos\\aws_eks_repo\\spring_calc_1\\src\\main\\java\\output.md");; 
    private static final String separator = System.getProperty("line.separator");

    public static void main(String[] args) throws Exception {
        if (Files.exists(destPath)) Files.delete(destPath);
        processJava();
        //processPage();
        // processSQL();
    }

    private static void processSQL() throws Exception {
        Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path inputFile, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println("\n##### " + inputFile.getFileName() + " #####");
                if (Files.isDirectory(inputFile)) return FileVisitResult.CONTINUE;
                if (inputFile.toString().endsWith(".sql")) {
                    String className = inputFile.getFileName().toString()
                            .replaceAll("(.*)\\.sql", "$1");
                    List<String> inputFileLines = Files.readAllLines(inputFile);
                    try {
                        String reqHeader = "";
                        /*
                        String reqHeader = """
                                SQLの仕様書を、Markdown形式で出力してください。
                                Markdownの見出しは、レベル4からにしてください。
                                ドイツ語でお願いします。
                                コメントもドイツ語に変換してください。
                                """;

                        String inputFileStr = convertToReqString(inputFileLines);
                        List<String> responseLines = askToOpenAI(reqHeader + separator +
                                inputFileStr);
                        String responseContentStr = "";
                        for (String line : responseLines) {
                            responseContentStr += line;
                        }
                        Matcher matcher = Pattern.compile(PACKAGE_REGEX)
                                .matcher(inputFileStr);
                        String packageName = matcher.find() ?
                                matcher.group().replaceAll(PACKAGE_REGEX, "$1") + "." :
                                    "";
                        responseContentStr = "## " + 
                                packageName + className +
                                separator +
                                responseContentStr +
                                separator +
                                separator;
                        writeMarkdown(responseContentStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return FileVisitResult.CONTINUE;
            };
        });
    }

    private static void processPage() throws Exception {
        Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path inputFile, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println("\n##### " + inputFile.getFileName() + " #####");
                if (Files.isDirectory(inputFile)) return FileVisitResult.CONTINUE;
                if (inputFile.toString().endsWith(".html") ||
                        inputFile.toString().endsWith(".js")) {
                    String className = inputFile.getFileName().toString()
                            .replaceAll("(.*)\\.html", "$1");
                    List<String> inputFileLines = Files.readAllLines(inputFile);
                    try {
                        String reqHeader = "";
                        /*
                        String reqHeader = """
                                JavaScriptコードの仕様書を、Markdown形式で出力してください。
                                30行程度で簡潔にお願いします。
                                Markdownの見出しは、レベル4からにしてください。
                                日本後でお願いします。
                                """;
                        String reqHeader = """
                                Thymeleafページの入出力項目一覧を、Markdownのテーブル形式で出力してください。
                                日本後でお願いします。
                                """;
                        */
                        String inputFileStr = convertToReqString(inputFileLines);
                        List<String> responseLines = askToOpenAI(reqHeader + separator +
                                inputFileStr);
                        String responseContentStr = "";
                        for (String line : responseLines) {
                            responseContentStr += line;
                        }
                        Matcher matcher = Pattern.compile(PACKAGE_REGEX)
                                .matcher(inputFileStr);
                        String packageName = matcher.find() ?
                                matcher.group().replaceAll(PACKAGE_REGEX, "$1") + "." :
                                    "";
                        responseContentStr = "## " + 
                                packageName + className +
                                separator +
                                responseContentStr +
                                separator +
                                separator;
                        writeMarkdown(responseContentStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return FileVisitResult.CONTINUE;
            };
        });
    }
    private static void processJava() throws Exception {
        Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path inputFile, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println("\n##### " + inputFile.getFileName() + " #####");
                if (Files.isDirectory(inputFile)) return FileVisitResult.CONTINUE;
                if (inputFile.toString().endsWith(".java")) {
                    String className = inputFile.getFileName().toString()
                            .replaceAll("(.*)\\.java", "$1") + "クラス";
                    List<String> inputFileLines = Files.readAllLines(inputFile);
                    try {
                        String reqHeader = "";
                        /*
                        String reqHeader = """
Veuillez présenter le plan de refactorisation du programme Java au format Markdown.
Veuillez commencer les titres Markdown à partir du niveau 4.
Merci de répondre en français.
                                """;
                        String reqHeader = """
                                Javaプログラムの仕様書を、Markdown形式で出力してください。
                                Markdownの見出しは、レベル4からにしてください。
                                日本語で作成をお願いします。
                                """;
                        String reqHeader = """
                                Javaプログラムのリファクタリング案を、Markdown形式で出力してください。
                                Markdownの見出しは、レベル4からにしてください。
                                日本後でお願いします。
                                """;

                        String reqHeader = """
                                Javaプログラムの定数、列挙型の一覧を、Markdownのテーブル形式で出力してください。
                                Markdownの見出しは、レベル4からにしてください。
                                日本後でお願いします。
                                """;
                        String reqHeader = """
                                Javaプログラムのメソッド概要を、Markdownのテーブル形式で出力してください。
                                日本後でお願いします。
                                """;
                        */
                        String inputFileStr = convertToReqString(inputFileLines);
                        List<String> responseLines = askToOpenAI(reqHeader + separator +
                                inputFileStr);
                        String responseContentStr = "";
                        for (String line : responseLines) {
                            responseContentStr += line;
                        }
                        Matcher matcher = Pattern.compile(PACKAGE_REGEX)
                                .matcher(inputFileStr);
                        String packageName = matcher.find() ?
                                matcher.group().replaceAll(PACKAGE_REGEX, "$1") + "." :
                                    "";
                        responseContentStr = "## " + 
                                packageName + className +
                                separator +
                                responseContentStr +
                                separator +
                                separator;
                        writeMarkdown(responseContentStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return FileVisitResult.CONTINUE;
            };
        });
    }

    private static String convertToReqString(List<String> content) {
        String reqString = "";
        for (String line : content) {
            reqString += line + System.getProperty("line.separator");
        }
        return reqString;
    }

    public static List<String> askToOpenAI(String requestContent) throws Exception {
        // HttpClientオブジェクトを生成する
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        Message message = new Message("user", requestContent);
        RequestBody requestBody = new RequestBody(MODEL, List.of(message), 0.7F);
        String requestStr = getRequestJson(requestBody);
        // System.out.println(requestStr);

        // HttpRequestオブジェクトを生成する
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + OPENAI_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestStr))
                .build();

        // HttpRequestを送信し、HTTPサーバーを非同期で呼び出す
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseStr = response.body();
        ResponseBody responseBody = getResponseBody(responseStr);
        List<String> responseLines = new ArrayList<>();
        responseBody.getChoices().forEach(choice -> {
            String content = choice.getMessage().getContent();
            responseLines.add(content);
        });
        return responseLines;
    }

    private static void writeMarkdown(String content) {
        try (FileWriter writer = new FileWriter(destPath.toString(), true)) {
           writer.append(content);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static String getRequestJson(RequestBody requestBody) {
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    private static ResponseBody getResponseBody(String responseStr) {
        ObjectMapper mapper = new ObjectMapper();
        ResponseBody responseBody = null;
        try {
            responseBody = mapper.readValue(responseStr, ResponseBody.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responseBody;
    }
}