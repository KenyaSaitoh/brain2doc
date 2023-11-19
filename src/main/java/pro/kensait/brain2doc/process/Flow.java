package pro.kensait.brain2doc.process;

import static pro.kensait.brain2doc.common.Const.*;
import static pro.kensait.brain2doc.common.Util.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import pro.kensait.brain2doc.config.DefaultValueHolder;
import pro.kensait.brain2doc.exception.OpenAIClientException;
import pro.kensait.brain2doc.exception.OpenAIInsufficientQuotaException;
import pro.kensait.brain2doc.exception.OpenAIInvalidAPIKeyException;
import pro.kensait.brain2doc.exception.OpenAIRateLimitExceededException;
import pro.kensait.brain2doc.exception.OpenAITokenLimitOverException;
import pro.kensait.brain2doc.exception.RetryCountOverException;
import pro.kensait.brain2doc.openai.ApiClient;
import pro.kensait.brain2doc.openai.ApiResult;
import pro.kensait.brain2doc.openai.SuccessResponseBody;
import pro.kensait.brain2doc.params.Parameter;
import pro.kensait.brain2doc.params.ResourceType;
import pro.kensait.brain2doc.process.TemplateAttacher.Prompt;
import pro.kensait.brain2doc.transform.TransformStrategy;

/*
 * 処理フローを表すクラス
 */
public class Flow {

    // Const
    private static final String ZIP_FILE_EXT = ".zip";
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String TIMEOUT_MESSAGE = "TIMEOUT";
    private static final String CLIENT_ERROR_MESSAGE = "CLIENT_ERROR";
    private static final String INVALID_API_KEY_MESSAGE = "INVALID_API_KEY";
    private static final String INSUFFICIENT_QUOTA_MESSAGE = "INSUFFICIENT_QUOTA";
    private static final String TOKEN_LIMIT_OVER_MESSAGE = "TOKEN_LIMIT_OVER";
    private static final String RATE_LIMIT_EXCEEDED_MESSAGE = "RATE_LIMIT_EXCEEDED";
    private static final String EXTRACT_TOKEN_COUNT_REGEX = "resulted in (\\d+) tokens";
    private static final String PROMPT_HEADING = "### PROMPT CONTENT (without source)";
    private static final String PROCESS_PROGRESS_HEADING = "### PROGRESS";
    private static final String REPORT_TITLE = "|SOURCE|STATUS|REQUEST TOKEN|RESPONSE TOKEN|PROCESS TIME|";
    private static final String REPORT_TABLE_DIVIDER = "|-|-|-|-|-|";

    private static Parameter param; // このクラス内のみで使われるグローバルな変数
    private static List<String> reportList = new CopyOnWriteArrayList<String>(); 

    synchronized public static void init(Parameter paramValues) {
        param = paramValues;
        reportList.clear();
        reportList.add(REPORT_TITLE);
        reportList.add(REPORT_TABLE_DIVIDER);
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

    /*
     * ディレクトリを渡り歩く
     */
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

    /*
     * ファイルを読み込む
     */
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

                List<String> inputFileLines = Files.readAllLines(
                        inputFilePath,
                        Charset.forName(param.getCharset()));
                mainProcess(inputFilePath, inputFileLines);
            }
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /*
     * ZIPファイルを読み込んで渡り歩く
     */
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

    /*
     * メイン処理
     */
    private static void mainProcess(Path inputFilePath, List<String> inputFileLines) {
        // 進捗が開始した時のタスク
        Runnable startProgressTask = () -> {
            System.out.print("- processing [" + inputFilePath.getFileName() + "] ");
        };

        // 最初のファイルではPROMPTを先に表示するため、ここではPROGRESSを表示しない
        if (! param.isPrintPrompt()) {
            startProgressTask.run(); // 2ファイル目以降はPROGRESSを表示する
        }

        // コンソールへの進捗バー表示スレッドを起動する
        CountDownLatch startSignal = new CountDownLatch(1);
        ConsoleProgressTask cpt = new ConsoleProgressTask(startSignal, false);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> future = executorService.submit(cpt);

        String inputFileContent = toStringFromStrList(inputFileLines);
        List<ApiResult> apiResultList = null;

        // 進捗が終了した時のタスク
        Runnable progressDoneTask = () -> {
            cpt.setDone(true);
            try {
                future.get();
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        };
        try {
            apiResultList = askToOpenAi(inputFileLines, inputFileContent,
                    1, // ファイル分割数
                    0, // 前回分割数（初回は0）
                    1, // 実行回数（初回は1）
                    startSignal, startProgressTask);
        } catch (OpenAIInvalidAPIKeyException oe) {
            addReport(inputFilePath, INVALID_API_KEY_MESSAGE, 0, 0, 0L);
            throw oe; // プログラム停止
        } catch (OpenAIInsufficientQuotaException oe) {
            addReport(inputFilePath, INSUFFICIENT_QUOTA_MESSAGE, 0, 0, 0L);
            throw oe; // プログラム停止
        } catch (OpenAITokenLimitOverException oe) {
            addReport(inputFilePath, TOKEN_LIMIT_OVER_MESSAGE, 0, 0, 0L);
            progressDoneTask.run();
            return; // 次のファイルへ
        } catch (OpenAIRateLimitExceededException oe) {
            addReport(inputFilePath, RATE_LIMIT_EXCEEDED_MESSAGE, 0, 0, 0L);
            progressDoneTask.run();
            return; // 次のファイルへ
        } catch (OpenAIClientException oe) {
            addReport(inputFilePath, CLIENT_ERROR_MESSAGE, 0, 0, 0L);
            throw oe; // プログラム停止
        } catch (RetryCountOverException oe) {
            addReport(inputFilePath, TIMEOUT_MESSAGE, 0, 0, 0L);
            throw oe; // プログラム停止
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
            addReport(inputFilePath, SUCCESS_MESSAGE,
                    apiResult.getResponseBody().getUsage().getPromptTokens(),
                    apiResult.getResponseBody().getUsage().getCompletionTokens(),
                    apiResult.getInterval());
        }
        progressDoneTask.run();
    }

    /*
     * OpenAIのAPIを呼び出す
     */
    private static List<ApiResult> askToOpenAi(
            List<String> inputFileLines,
            String inputFileContent,
            int splitConut,
            int prevSplitCount,
            int tryCount,
            CountDownLatch startSignal,
            Runnable startProgressTask) {
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
                printPrompt(prompt);
                System.out.println(PROCESS_PROGRESS_HEADING + LINE_SEP);
                startProgressTask.run();
                param.setPrintPrompt(false);
            }

            // プログレスバーの表示を開始する
            startSignal.countDown();

            String userMessageContent = toStringFromStrList(prompt.getUserMessageLines());

            // OpenAIのAPIを呼び出す
            ApiResult apiResult = null;
            
            // OpenAITokenLimitOverExceptionまたはレートリミットオーバーの場合はリトライするため、
            // 例外ハンドリングする
            try { 
                apiResult = ApiClient.ask(
                        prompt.getSystemMessage(),
                        prompt.getAssistantMessage(),
                        userMessageContent,
                        param.getTemparature(),
                        param.getOpenaiURL(),
                        param.getOpenaiModel(),
                        param.getOpenaiApikey(),
                        param.getProxyURL(),
                        param.getConnectTimeout(),
                        param.getRequestTimeout(),
                        param.getRetryCount(),
                        param.getRetryInterval());

            // トークンリミットオーバーの場合
            } catch (OpenAITokenLimitOverException oe) {
                if (param.isAutoSplitMode()) { // 自動分割モードの場合

                    // エラーメッセージからトークン数を抽出し、分割数を計算する
                    String errorMessage =
                            oe.getClientErrorBody().getError().getMessage();
                    Double tokenCount = extractToken(userMessageContent, errorMessage);
                    if (tokenCount == null)
                        throw new OpenAITokenLimitOverException(oe.getClientErrorBody());
                    @SuppressWarnings("rawtypes")
                    Map tokenLimitMap = (Map) (ConstMapHolder.getConstMap()
                            .get("token-count-limit"));
                    int tokenCountLimit = (int)
                            (tokenLimitMap.get(param.getOpenaiModel()));
                    int newSplitConut = SplitUtil.calcSplitCount(
                            tokenCount, tokenCountLimit);

                    // 分割数が設定値を超えていた場合は再帰呼び出ししない
                    if (param.getMaxSplitCount() <= newSplitConut) {
                        throw oe;
                    }

                    tryCount++;

                    System.out.print("[FILE_SPLIT]");

                    // 分割数を指定して再帰呼び出しする
                    return askToOpenAi(inputFileLines, inputFileContent,
                            newSplitConut, splitConut, tryCount,
                            startSignal, startProgressTask);
                }
                throw oe;

            // レートリミットオーバーの場合
            } catch (OpenAIRateLimitExceededException oe) {
                if (param.isAutoSplitMode()) { // 自動分割モードの場合
                    // gpt-4でOpenAIRateLimitExceededExceptionした場合は、
                    // エラーメッセージからトークン数が分かるわけではないので、分割数は
                    // 以下のように決める。
                    // 要は、2度目のときは2分割、3度目のときは3分割と、リトライ回数に応じて
                    // 分割数を加算する。
                    tryCount++;
                    int newSplitConut = tryCount;

                    // 分割数が設定値を超えていた場合は再帰呼び出ししない
                    if (param.getMaxSplitCount() <= newSplitConut) {
                        throw oe;
                    }

                    System.out.print("[FILE_SPLIT]");

                    // 次の呼び出し前に、一定期間、間隔をあける
                    sleepAWhile(param.getRetryInterval());

                    // 分割数を指定して再帰呼び出しする
                    return askToOpenAi(inputFileLines, inputFileContent,
                            newSplitConut, splitConut, tryCount,
                            startSignal, startProgressTask);
                    }
                throw oe;
            }
            apiResultList.add(apiResult);
        }
        return apiResultList;
    }

    /*
     * レスポンスメッセージからトークン数を抽出する
     */
    private static Double extractToken(String content, String message) {
        Matcher matcher = Pattern.compile(EXTRACT_TOKEN_COUNT_REGEX).matcher(message);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return null; 
    }

    /*
     * ファイル名から拡張子を取り除いて返す
     */
    private static String extractNameWithoutExt(String fileName, String ext) {
        int lastExtIndex = fileName.lastIndexOf(ext);
        return fileName.substring(0, lastExtIndex);
    }

    /*
     * 文字列リストから文字列を返す
     */
    private static String toStringFromStrList(List<String> strList) {
        String content = "";
        for (String line : strList) {
            content += line + LINE_SEP;
        }
        return content;
    }

    /*
     * レスポンスに含まれるChoiceから、文字列リストを返す
     */
    private static List<String> toChoicesFromResponce(SuccessResponseBody responseBody) {
        List<String> responseChoiceList = new ArrayList<>();
        responseBody.getChoices().forEach(choice -> {
            String responseContent = choice.getMessage().getContent();
            responseChoiceList.add(responseContent);
        });
        return responseChoiceList;
    }
    
    /*
     * Choiceリストから文字列リストに変換する
     */
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

    /*
     * APIからの応答をファイルに書き込む
     */
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
        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(param.getDestFilePath().toString(), true),
                Charset.forName(DefaultValueHolder.getProperty("charset")))) {
            writer.append(responseContent);
            writer.flush();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /*
     * プロンプトをコンソールに表示する
     */
    private static void printPrompt(Prompt prompt) {
        System.out.println(PROMPT_HEADING + LINE_SEP);
        System.out.println(prompt.getSystemMessage());
        System.out.println(prompt.getAssistantMessage());
        System.out.println(prompt.getUserMessage());
    }

    /*
     * レポートに処理結果を追加する
     */
    private static void addReport(Path inputFilePath, String message,
            int resuestTokenCount, int responseTokenCount,
            long interval) {
        String report = "|" +
            inputFilePath.getFileName().toString() + "|" + 
            message + "|" + 
            resuestTokenCount + "|" +
            responseTokenCount + "|"  +
            interval + "|";
        reportList.add(report);
    }
}