package pro.kensait.brain2doc.process;

import static pro.kensait.brain2doc.common.ConsoleColor.*;
import static pro.kensait.brain2doc.common.Const.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import pro.kensait.brain2doc.common.Const;
import pro.kensait.brain2doc.config.ConstMapHolder;
import pro.kensait.brain2doc.exception.OpenAIClientException;
import pro.kensait.brain2doc.exception.OpenAIRateLimitExceededException;
import pro.kensait.brain2doc.exception.OpenAIRetryCountOverException;
import pro.kensait.brain2doc.exception.OpenAITokenLimitOverException;
import pro.kensait.brain2doc.openai.ApiClient;
import pro.kensait.brain2doc.openai.ApiResult;
import pro.kensait.brain2doc.openai.SuccessResponseBody;
import pro.kensait.brain2doc.params.Parameter;
import pro.kensait.brain2doc.params.ResourceType;
import pro.kensait.brain2doc.process.TemplateAttacher.Prompt;
import pro.kensait.brain2doc.transform.TransformStrategy;

public class Flow {
    private static final String ZIP_FILE_EXT = ".zip";
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String TIMEOUT_MESSAGE = "TIMEOUT";
    private static final String LIMIT_EXCEEDED_MESSAGE = "LIMIT_EXCEEDED";
    private static final String CONTEXT_LENGTH_EXCEEDED_CODE = "context_length_exceeded";
    private static final String RATE_LIMIT_EXCEEDED_CODE = "rate_limit_exceeded";
    private static final String TOKEN_LIMIT_OVER_MESSAGE = "TOKEN_LIMIT_OVER";
    private static final String CLIENT_ERROR_MESSAGE = "CLIENT_ERROR";
    private static final String EXTRACT_TOKEN_COUNT_REGEX = "resulted in (\\d+) tokens";
    private static final String PROMPT_HEADING = "### PROMPT CONTENT (without source)";
    private static final String PROCESS_PROGRESS_HEADING = "### PROGRESS";

    private static Parameter param; // このクラス内のみで使われるグローバルな変数
    private static List<String> reportList = new CopyOnWriteArrayList<String>(); 

    synchronized public static void init(Parameter paramValues) {
        param = paramValues;
        reportList.clear();
    }

    public static List<String> getReportList() {
        return reportList;
    }

    public static void startAndFork() {
        if (Files.isDirectory(param.getSrcPath())) {
            walkDirectory(param.getSrcPath());
        } else {
            if (param.getSrcPath().getFileName().toString().endsWith(ZIP_FILE_EXT)) {
                readZipFile(param.getSrcPath());
            } else {
                readNormalFile(param.getSrcPath());
            }
        }
    }

    private static void walkDirectory(Path srcPath) {
        try {
        Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path inputFilePath, BasicFileAttributes attrs)
                    throws IOException {
                
                if (Files.isDirectory(inputFilePath)) return FileVisitResult.CONTINUE;
                readNormalFile(inputFilePath);
                return FileVisitResult.CONTINUE;
            };
        });
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static void readNormalFile(Path inputFilePath) {
        ResourceType resourceType = param.getResourceType();
        try {
            if (resourceType.matchesExt(inputFilePath.toString())) {
                String ext = resourceType.getMatchExt(inputFilePath.toString());
                // 正規表現がパラメータとして指定されており、かつ、
                // 拡張子を取り除いたファイル名が、正規表現とマッチしなかった場合は、
                // 当該ファイルを対象外と見なし、直ちに処理を折り返す
                if (param.getSrcRegex() != null &&
                        ! extractNameWithoutExt(
                                inputFilePath.getFileName().toString(), ext)
                        .matches(param.getSrcRegex())) {
                    return;
                }
                List<String> inputFileLines = Files.readAllLines(inputFilePath);
                mainProcess(inputFilePath, inputFileLines);
            }
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static void readZipFile(Path srcPath) {
        ResourceType resourceType = param.getResourceType();
        ZipInputStream zis = null;
        try {
            zis = new ZipInputStream(new FileInputStream(srcPath.toFile()));
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                Path inputFilePath = Paths.get(srcPath.toString(), entryName);
                if (resourceType.matchesExt(entryName)) {
                    String ext = resourceType.getMatchExt(entryName.toString());
                    // 正規表現がパラメータとして指定されており、かつ、
                    // 拡張子を取り除いたファイル名が、正規表現とマッチしなかった場合は、
                    // 当該ファイルを対象外と見なし、直ちに処理を折り返す
                    if (param.getSrcRegex() != null &&
                            ! extractNameWithoutExt(entryName, ext)
                            .matches(param.getSrcRegex())) {
                        continue;
                    }
                    BufferedReader br = new BufferedReader(new InputStreamReader(zis));
                    List<String> inputFileLines = new ArrayList<>();
                    String line;
                    while ((line = br.readLine()) != null) {
                        inputFileLines.add(line);
                    }
                    mainProcess(inputFilePath, inputFileLines);
                }
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            try {
                zis.close();
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
    }

    private static void mainProcess(Path inputFilePath, List<String> inputFileLines) {
        Runnable printProcessing = () -> {
            System.out.print("processing [" + ANSI_BOLD + ANSI_PURPLE +
                    inputFilePath.getFileName() + ANSI_RESET + "] ");
        };
        // 最初のファイルではPROMPTを先に表示するため、ここではPROGRESSを表示しない
        if (! param.isPrintPrompt()) {
            printProcessing.run(); // 2ファイル目以降はPROGRESSを表示する
        }

        // コンソールへの進捗バー表示スレッドを起動する
        CountDownLatch startSignal = new CountDownLatch(1);
        ConsoleProgressTask cpt = new ConsoleProgressTask(startSignal, false);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> future = executorService.submit(cpt);

        String inputFileContent = toStringFromStrList(inputFileLines);
        List<ApiResult> apiResultList = null;
        try {
            apiResultList = askToOpenAi(inputFileLines, inputFileContent, 1, 0,
                    startSignal, printProcessing);
        } catch (OpenAITokenLimitOverException oe) {
            addReport(inputFilePath, TOKEN_LIMIT_OVER_MESSAGE, 0);
            return; // 次のファイルへ
        } catch (OpenAIRateLimitExceededException ore) {
            addReport(inputFilePath, LIMIT_EXCEEDED_MESSAGE, 0);
            throw ore; // プログラム停止
        } catch (OpenAIClientException oce) {
            addReport(inputFilePath, CLIENT_ERROR_MESSAGE, 0);
            throw oce; // プログラム停止
        } catch (OpenAIRetryCountOverException ore) {
            addReport(inputFilePath, TIMEOUT_MESSAGE, 0);
            throw ore; // プログラム停止
        }

        for (int i = 0; i < apiResultList.size(); i++) {
            ApiResult apiResult = apiResultList.get(i);
            List<String> responseChoices = toChoicesFromResponce(apiResult.getResponseBody());
            List<String> responseLines = toLineListFromChoices(responseChoices);
            TransformStrategy transStrategy = TransformStrategy.getOutputStrategy(
                    param.getResourceType(),
                    param.getGenerateType());
            String outputFileContent = transStrategy.transform(
                    inputFilePath,
                    inputFileContent,
                    responseLines,
                    i + 1);
            write(outputFileContent);
            addReport(inputFilePath, SUCCESS_MESSAGE, apiResult.getInterval());
        }
        cpt.setDone(true);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static List<ApiResult> askToOpenAi(
            List<String> inputFileLines,
            String inputFileContent,
            int splitConut,
            int prevSplitCount,
            CountDownLatch startSignal,
            Runnable printProcessing) {
        // TODO
        // System.out.println(splitConut + "," + prevSplitCount);
        if (prevSplitCount != 0) {
            if (splitConut <= prevSplitCount) {
                throw new OpenAITokenLimitOverException(
                        "「エラーメッセージ上のトークンリミット」を利用した分割に、"
                        + "何らかの理由で失敗しました");
            }
        }

        List<List<String>> splittedInputFileLists = null;
        if (splitConut == 1) {
            splittedInputFileLists = new ArrayList<>();
            splittedInputFileLists.add(inputFileLines);
        } else {
            splittedInputFileLists = SplitUtil.split(inputFileLines, splitConut);
        }

        List<ApiResult> apiResultList = new ArrayList<>();
        for (int i = 0; i < splittedInputFileLists.size(); i++) {
            List<String> eachInputFileLines = splittedInputFileLists.get(i);
            // 分割された入力ファイルに、テンプレートをアタッチする
            Prompt prompt = TemplateAttacher.attach(eachInputFileLines,
                    param.getResourceType(),
                    param.getGenerateType(),
                    param.getGenTable(),
                    param.getFields(),
                    param.getOutputScaleType(),
                    param.getLocale(),
                    param.getTemplateFile(),
                    i);

            // 実行ごとに初回のみ
            if (param.isPrintPrompt()) {
                // プログレスバーより先にプロンプトを出力する
                printPrompt(prompt.getPromptMessage());
                System.out.println(PROCESS_PROGRESS_HEADING);
                printProcessing.run();
                param.setPrintPrompt(false);
            }

            // プログレスバーの表示を開始する
            startSignal.countDown();

            String requestContent = toStringFromStrList(prompt.getRequestLines());

            // OpenAIのAPIを呼び出す
            ApiResult apiResult = null;
            try {
                apiResult = ApiClient.ask(requestContent,
                        param.getOpenaiURL(),
                        param.getOpenaiModel(),
                        param.getOpenaiApikey(),
                        param.getProxyURL(),
                        param.getConnectTimeout(),
                        param.getRequestTimeout(),
                        param.getRetryCount(),
                        param.getRetryInterval());
            } catch (OpenAIClientException oce) {

                // トークンリミットオーバーの場合
                if (Objects.equals(CONTEXT_LENGTH_EXCEEDED_CODE,
                        oce.getClientErrorBody().getError().getCode())) {

                    if (param.isAutoSplitMode()) { // 自動分割モードの場合

                        // エラーメッセージからトークン数を抽出し、分割数を計算する
                        String errorMessage =
                                oce.getClientErrorBody().getError().getMessage();
                        Double tokenCount = extractToken(requestContent, errorMessage);
                        if (tokenCount == null)
                            throw new OpenAITokenLimitOverException(oce.getClientErrorBody());
                        @SuppressWarnings("rawtypes")
                        Map tokenLimitMap = (Map) (ConstMapHolder.getConstMap()
                                .get("token-count-limit"));
                        int tokenCountLimit = (int)
                                (tokenLimitMap.get(param.getOpenaiModel()));
                        int newSplitConut = SplitUtil.calcSplitCount(
                                tokenCount, tokenCountLimit);

                        // 分割数を指定して再帰呼び出しする
                        return askToOpenAi(inputFileLines, inputFileContent,
                                newSplitConut, splitConut, startSignal, printProcessing);
                    }
                    throw new OpenAITokenLimitOverException(oce.getClientErrorBody());

                } else if (Objects.equals(RATE_LIMIT_EXCEEDED_CODE,
                        oce.getClientErrorBody().getError().getCode())) {
                    throw new OpenAIRateLimitExceededException(oce.getClientErrorBody());
                }
                // RetryCountOverでもRateLimitExceededでもない場合は、OpenAIClientExceptionを再スローする
                throw oce;
            }
            apiResultList.add(apiResult);
        }
        return apiResultList;
    }

    private static Double extractToken(String content, String message) {
        Matcher matcher = Pattern.compile(EXTRACT_TOKEN_COUNT_REGEX).matcher(message);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return null; 
    }

    private static String extractNameWithoutExt(String fileName, String ext) {
        int lastExtIndex = fileName.lastIndexOf(ext);
        return fileName.substring(0, lastExtIndex);
    }

    private static String toStringFromStrList(List<String> strList) {
        String content = "";
        for (String line : strList) {
            content += line + LINE_SEP;
        }
        return content;
    }

    private static List<String> toChoicesFromResponce(SuccessResponseBody responseBody) {
        List<String> responseChoiceList = new ArrayList<>();
        responseBody.getChoices().forEach(choice -> {
            String responseContent = choice.getMessage().getContent();
            responseChoiceList.add(responseContent);
        });
        return responseChoiceList;
    }
    
    // 複数のChoiceがリストで返されるので、それを文字列リストに変換する
    private static List<String> toLineListFromChoices(
            List<String> responseChoices) {
        List<String> responseLines = new ArrayList<>();
        for (String responseChoice :responseChoices) {
            for (String line : responseChoice.split(Const.LINE_SEP)) {
                responseLines.add(line);
            }
        }
        return responseLines;
    }

    private static void write(String responseContent) {
        Path targetDir;
        if (Files.isDirectory(param.getDestFilePath())) {
            targetDir = param.getDestFilePath();
        } else {
            targetDir = param.getDestFilePath().getParent();
        }
        try {
            Files.createDirectories(targetDir);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        try (FileWriter writer = new FileWriter(param.getDestFilePath().toString(),
                true)) {
            writer.append(responseContent);
            writer.flush();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static void printPrompt(String promptMessage) {
        System.out.println(PROMPT_HEADING);
        System.out.print(ANSI_CYAN +  promptMessage);
        System.out.println(ANSI_RESET);
    }

    private static void addReport(Path inputFilePath, String message, long interval) {
        String report = inputFilePath.getFileName().toString() + "," +
                message + "," + interval;
        reportList.add(report);
    }
}