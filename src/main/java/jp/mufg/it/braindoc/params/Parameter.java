package jp.mufg.it.braindoc.params;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jp.mufg.it.braindoc.common.Const;
import jp.mufg.it.braindoc.config.DefaultPropsHolder;

public class Parameter {
    /*
    private static final String DEFAULT_OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    private static final String DEFAULT_OPENAI_MODEL = "gpt-3.5-turbo";
    private static final int DEFAULT_CONNECT_TIMEOUT = 10;
    private static final int DEFAULT_REQUEST_TIMEOUT = 300;
    private static final int DEFAULT_RETRY_COUNT = 5;
    private static final boolean DEFAULT_STOP_ON_FAILURE = false;
    private static final String DEFAULT_OUTPUT_FILE_NAME = "brain-doc-output";
     */

    private String openaiURL; // デフォルト値はプロパティファイルから
    private String openaiModel; // デフォルト値はプロパティファイルから
    private String openaiApikey; // 指定必須
    private ResourceType resourceType; // デフォルト値はプロパティファイルから
    private ProcessType processType; // デフォルト値はプロパティファイルから
    private ScaleType scaleType; // デフォルト値はプロパティファイルから
    private Path srcPath; // 指定必須
    private Path destFilePath; // デフォルト値はソースパスと同じディレクトリの固定ファイル名
    private int connectTimeout; // デフォルト値はプロパティファイルから
    private int requestTimeout; // デフォルト値はプロパティファイルから
    private int retryCount; // デフォルト値はプロパティファイルから
    private boolean stopOnFailure; // デフォルト値はfalse

    public static Parameter of(String[] args) {
        String openaiUrl = DefaultPropsHolder.getProperty("openai_url");
        String openaiModel = DefaultPropsHolder.getProperty("openai_model");
        String openaiApiKey = System.getenv("OPENAI_APY_KEY");
        ResourceType resourceType = ResourceType.valueOf(
                DefaultPropsHolder.getProperty("resource"));
        ProcessType processType = ProcessType.valueOf(
                DefaultPropsHolder.getProperty("process"));
        ScaleType scaleType = ScaleType.valueOf(
                DefaultPropsHolder.getProperty("scale"));
        String srcParam = null;
        String destParam = null;
        int connectTimeout = Integer.parseInt(
                DefaultPropsHolder.getProperty("connect_timeout"));
        int requestTimeout = Integer.parseInt(
                DefaultPropsHolder.getProperty("request_timeout"));
        int retryCount = Integer.parseInt(
                DefaultPropsHolder.getProperty("retry_count"));

        boolean stopOnFailure = false;
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equalsIgnoreCase("--url")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    openaiUrl = args[++i];
                } else if (args[i].equalsIgnoreCase("--model")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    openaiModel = args[++i];
                } else if (args[i].equalsIgnoreCase("--apikey")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    openaiApiKey = args[++i];
                } else if (args[i].equalsIgnoreCase("--resource")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    resourceType = ResourceType.valueOf(args[++i]);
                } else if (args[i].equalsIgnoreCase("--process")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    processType = ProcessType.valueOf(args[++i]);
                } else if (args[i].equalsIgnoreCase("--scale")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    scaleType = ScaleType.valueOf(args[++i]);
                } else if (args[i].equalsIgnoreCase("--src")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    srcParam = args[++i];
                } else if (args[i].equalsIgnoreCase("--dest")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    destParam = args[++i];
                } else if (args[i].equalsIgnoreCase("--connectTimeout")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    connectTimeout = Integer.parseInt(args[++i]);
                } else if (args[i].equalsIgnoreCase("--requestTimeout")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    requestTimeout = Integer.parseInt(args[++i]);
                } else if (args[i].equalsIgnoreCase("--retryCount")) {
                    if (args[i + 1].startsWith("-"))
                        continue;
                    retryCount = Integer.parseInt(args[++i]);
                } else if (args[i].equalsIgnoreCase("--stopOnFailure")) {
                    stopOnFailure = true;
                } else {
                    throw new IllegalArgumentException(
                            "存在しないパラメータが指定されました: \"" + args[i] + "\"");
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        // APIKeyを決める
        if (openaiApiKey == null || openaiApiKey.equals(""))
            throw new IllegalArgumentException("APIキーが指定されていません");

        // 入力元パスと入力元ディレクトリを決める
        if (srcParam == null || srcParam.equals(""))
            throw new IllegalArgumentException("ソースが指定されていません");
        Path srcPath = Paths.get(srcParam);
        Path srcDirPath = null;
        if (Files.isDirectory(srcPath)) {
            srcDirPath = srcPath;
        } else {
            srcDirPath = srcPath.getParent();
            throw new IllegalArgumentException("ソースが存在しません");
        }

        // 出力先パスを決める
        Path destPath = null;
        if (destParam == null || destParam.equals("")) {
            // destオプションの指定がなかった場合は、destPathはソースパス＋デフォルト名
            destPath = Paths.get(srcDirPath.toString(),
                    DefaultPropsHolder.getProperty("output_file_name")
                            + Const.OUTPUT_FILE_EXT);
        } else {
            // destオプションの指定があった場合は、ディレクトリ指定だった場合は
            // デフォルトファイル名を採用し、ファイル指定だった場合はそのまま
            destPath = Paths.get(destParam);
            if (Files.isDirectory(destPath)) {
                destPath = Paths.get(destPath.toString(),
                        DefaultPropsHolder.getProperty("output_file_name") +
                                Const.OUTPUT_FILE_EXT);
            }
        }

        return new Parameter(openaiUrl, openaiModel, openaiApiKey, resourceType,
                processType, scaleType, srcPath, destPath, connectTimeout, requestTimeout,
                retryCount, stopOnFailure);

    }

    public Parameter(String openaiURL, String openaiModel, String openaiApikey,
            ResourceType resourceType, ProcessType processType, ScaleType scaleType,
            Path srcPath, Path destFilePath, int connectTimeout, int requestTimeout,
            int retryCount, boolean stopOnFailure) {
        super();
        this.openaiURL = openaiURL;
        this.openaiModel = openaiModel;
        this.openaiApikey = openaiApikey;
        this.resourceType = resourceType;
        this.processType = processType;
        this.scaleType = scaleType;
        this.srcPath = srcPath;
        this.destFilePath = destFilePath;
        this.connectTimeout = connectTimeout;
        this.requestTimeout = requestTimeout;
        this.retryCount = retryCount;
        this.stopOnFailure = stopOnFailure;
    }

    public String getOpenaiURL() {
        return openaiURL;
    }

    public void setOpenaiURL(String openaiURL) {
        this.openaiURL = openaiURL;
    }

    public String getOpenaiModel() {
        return openaiModel;
    }

    public void setOpenaiModel(String openaiModel) {
        this.openaiModel = openaiModel;
    }

    public String getOpenaiApikey() {
        return openaiApikey;
    }

    public void setOpenaiApikey(String openaiApikey) {
        this.openaiApikey = openaiApikey;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ProcessType getProcessType() {
        return processType;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    public ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public Path getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(Path srcPath) {
        this.srcPath = srcPath;
    }

    public Path getDestFilePath() {
        return destFilePath;
    }

    public void setDestFilePath(Path destFilePath) {
        this.destFilePath = destFilePath;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isStopOnFailure() {
        return stopOnFailure;
    }

    public void setStopOnFailure(boolean stopOnFailure) {
        this.stopOnFailure = stopOnFailure;
    }

    @Override
    public String toString() {
        return "Parameter [openaiURL=" + openaiURL + ", openaiModel=" + openaiModel
                + ", openaiApikey=" + openaiApikey + ", resourceType=" + resourceType
                + ", processType=" + processType + ", scaleType=" + scaleType
                + ", srcPath=" + srcPath + ", destFilePath=" + destFilePath
                + ", connectTimeout=" + connectTimeout + ", requestTimeout="
                + requestTimeout + ", retryCount=" + retryCount + ", stopOnFailure="
                + stopOnFailure + "]";
    }
}