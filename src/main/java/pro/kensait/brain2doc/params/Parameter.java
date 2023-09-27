package pro.kensait.brain2doc.params;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import pro.kensait.brain2doc.common.Const;
import pro.kensait.brain2doc.config.DefaultValueHolder;

public class Parameter {
    private static final String OPENAI_API_KEY = "OPENAI_API_KEY";
    private static Parameter parameter;

    public static Parameter getParameter() {
        return parameter;
    }

    private String openaiURL; // デフォルト値はプロパティファイルから
    private String openaiModel; // デフォルト値はプロパティファイルから
    private String openaiApikey; // デフォルト値は環境変数から
    private ResourceType resourceType; // デフォルト値はプロパティファイルから
    private OutputType outputType; // デフォルト値はプロパティファイルから
    private OutputScaleType outputScaleType; // デフォルト値はプロパティファイルから
    private Path srcPath; // 指定必須
    private String srcRegex; // 任意指定
    private Path destFilePath; // デフォルト値はソースパスと同じディレクトリの固定ファイル名
    private Locale locale; // デフォルト値はプロパティファイルから
    private Path templateFile; // 任意指定
    private String proxyURL; // 任意指定
    private int connectTimeout; // デフォルト値はプロパティファイルから
    private int requestTimeout; // デフォルト値はプロパティファイルから
    private int retryCount; // デフォルト値はプロパティファイルから
    private int retryInterval; // デフォルト値はプロパティファイルから
    private boolean isAutoSplitMode; // デフォルト値false

    synchronized public static void setUp(String[] args) {
        String openaiUrl = DefaultValueHolder.getProperty("openai_url");
        String openaiModel = DefaultValueHolder.getProperty("openai_model");
        String openaiApiKey = System.getenv(OPENAI_API_KEY);
        // TODO System.out.println("###" + openaiApiKey);
        ResourceType resourceType = ResourceType.valueOf(
                DefaultValueHolder.getProperty("resource").toUpperCase());
        OutputType outputType = OutputType.valueOf(
                DefaultValueHolder.getProperty("output").toUpperCase());
        OutputScaleType outputScaleType = OutputScaleType.valueOf(
                DefaultValueHolder.getProperty("output_scale").toUpperCase());
        String srcParam = null;
        String srcRegex = null;
        String destParam = null;
        String langParam = DefaultValueHolder.getProperty("lang");
        String templateFileParam = null;
        String proxyURL = null;
        int connectTimeout = Integer.parseInt(
                DefaultValueHolder.getProperty("connect_timeout"));
        int requestTimeout = Integer.parseInt(
                DefaultValueHolder.getProperty("timeout"));
        int retryCount = Integer.parseInt(
                DefaultValueHolder.getProperty("retry_count"));
        int retryInterval = Integer.parseInt(
                DefaultValueHolder.getProperty("retry_interval"));
        boolean isAutoSplitMode = false;
        
        try {
            for (int i = 0; i < args.length; i++) {
                try {
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
                        resourceType = ResourceType.getResourceTypeByName(args[++i].toLowerCase());
                        if (resourceType == null) {
                            resourceType = ResourceType.OTHERS;
                        }
                    } else if (args[i].equalsIgnoreCase("--output")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        outputType = OutputType.getOutputTypeByName(args[++i].toLowerCase());
                        if (outputType == null) throw new IllegalArgumentException();
                    } else if (args[i].equalsIgnoreCase("--output-scale")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        outputScaleType = OutputScaleType
                                .getOutputScaleTypeByName(args[++i].toLowerCase());
                        if (outputScaleType == null) throw new IllegalArgumentException();
                    } else if (args[i].equalsIgnoreCase("--src")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        srcParam = args[++i];
                    } else if (args[i].equalsIgnoreCase("--regex")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        srcRegex = args[++i];
                    } else if (args[i].equalsIgnoreCase("--dest")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        destParam = args[++i];
                    } else if (args[i].equalsIgnoreCase("--lang")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        langParam = args[++i];
                    } else if (args[i].equalsIgnoreCase("--template")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        templateFileParam = args[++i];
                    } else if (args[i].equalsIgnoreCase("--proxyURL")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        proxyURL = args[++i];
                    } else if (args[i].equalsIgnoreCase("--connect-timeout")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        connectTimeout = Integer.parseInt(args[++i]);
                    } else if (args[i].equalsIgnoreCase("--timeout")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        requestTimeout = Integer.parseInt(args[++i]);
                    } else if (args[i].equalsIgnoreCase("--retry-count")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        retryCount = Integer.parseInt(args[++i]);
                    } else if (args[i].equalsIgnoreCase("--retry-interval")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        retryInterval = Integer.parseInt(args[++i]);
                    } else if (args[i].equalsIgnoreCase("--auto-split")) {
                        isAutoSplitMode = true;
                    } else {
                        throw new IllegalArgumentException(
                                "パラメータが不正です => \"" + args[i] + "\"");
                    }
                } catch (IllegalArgumentException ex) {
                    // Enumの列挙子がないパラメータが指定された場合はここに来る
                    throw new IllegalArgumentException(
                            "パラメータが不正です => \"" + args[i] + "\"");
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            // 最後のオプションにパラメータの指定がなかった場合はここにくる
            // 無視する
        }

        // APIKeyのチェック
        if (openaiApiKey == null || openaiApiKey.equals(""))
            throw new IllegalArgumentException("APIキーが指定されていません");

        // 入力元パスをチェックし、パラメータから入力元パスと入力元ディレクトリを決める
        if (srcParam == null || srcParam.equals(""))
            throw new IllegalArgumentException("ソースが指定されていません");
        Path srcPath = Paths.get(srcParam);
        if (! Files.exists(srcPath)) {
            throw new IllegalArgumentException("ソースが存在しません");
        }

        Path srcDirPath = null; // ソースパスがファイルの場合もあるので、ソースのディレクトリパスを用意する
        if (Files.isDirectory(srcPath)) {
            srcDirPath = srcPath;
        } else {
            srcDirPath = srcPath.getParent();
        }

        // 出力先パスを決める
        Path destPath = null;
        if (destParam == null || destParam.equals("")) {
            // destオプションの指定がなかった場合は、destPathはソースパス＋デフォルト名
            destPath = Paths.get(srcDirPath.toString(), 
                    getDefaultOutputFileName(resourceType, outputType));
        } else {
            // destオプションの指定があった場合は、ディレクトリ指定だった場合は
            // デフォルトファイル名を採用し、ファイル指定だった場合はそのまま
            destPath = Paths.get(destParam);
            if (Files.isDirectory(destPath)) {
                destPath = Paths.get(destPath.toString(), 
                        getDefaultOutputFileName(resourceType, outputType));
            }
        }

        // ロケールを決める
        Locale locale = new Locale(langParam);

        // 外部指定されたテンプレートファイルを決める
        Path templateFile = templateFileParam != null ?
                Paths.get(templateFileParam) :
                    null;

        parameter = new Parameter(openaiUrl, openaiModel, openaiApiKey,
                resourceType, outputType, outputScaleType,
                srcPath, srcRegex, destPath,
                locale, templateFile,
                proxyURL, connectTimeout, requestTimeout, retryCount, retryInterval,
                isAutoSplitMode);
    }

    private static String getDefaultOutputFileName(ResourceType resourceType,
            OutputType outputType) {
        return DefaultValueHolder.getProperty("output_file_name") + "-" +
                resourceType.getName() + "-" +
                outputType.getName() + "-" + 
                getCurrentDateTimeStr() +
                Const.OUTPUT_FILE_EXT;
    }

    private static String getCurrentDateTimeStr() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yMMddHHmmss");
        return localDateTime.format(dateTimeFormatter);
    } 

    private Parameter(String openaiURL, String openaiModel, String openaiApikey,
            ResourceType resourceType, OutputType outputType, OutputScaleType outputScaleType,
            Path srcPath, String srcRegex, Path destFilePath,
            Locale locale, Path templateFile,
            String proxyURL, int connectTimeout, int requestTimeout, int retryCount,
            int retryInterval,
            boolean isAutoSplitMode) {
        super();
        this.openaiURL = openaiURL;
        this.openaiModel = openaiModel;
        this.openaiApikey = openaiApikey;
        this.resourceType = resourceType;
        this.outputType = outputType;
        this.outputScaleType = outputScaleType;
        this.srcPath = srcPath;
        this.srcRegex = srcRegex;
        this.destFilePath = destFilePath;
        this.locale = locale;
        this.templateFile = templateFile;
        this.proxyURL = proxyURL;
        this.connectTimeout = connectTimeout;
        this.requestTimeout = requestTimeout;
        this.retryCount = retryCount;
        this.retryInterval = retryInterval;
        this.isAutoSplitMode = isAutoSplitMode;
    }

    public String getOpenaiURL() {
        return openaiURL;
    }

    public String getOpenaiModel() {
        return openaiModel;
    }

    public String getOpenaiApikey() {
        return openaiApikey;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public OutputType getOutputType() {
        return outputType;
    }

    public OutputScaleType getOutputScaleType() {
        return outputScaleType;
    }

    public Path getSrcPath() {
        return srcPath;
    }

    public String getSrcRegex() {
        return srcRegex;
    }

    public Path getDestFilePath() {
        return destFilePath;
    }

    public Locale getLocale() {
        return locale;
    }

    public Path getTemplateFile() {
        return templateFile;
    }

    public String getProxyURL() {
        return proxyURL;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public boolean isAutoSplitMode() {
        return isAutoSplitMode;
    }

    @Override
    public String toString() {
        return "Parameter [openaiURL=" + openaiURL + ", openaiModel=" + openaiModel
                + ", openaiApikey=" + openaiApikey + ", resourceType=" + resourceType
                + ", outputType=" + outputType + ", outputScaleType=" + outputScaleType
                + ", srcPath=" + srcPath + ", srcRegex=" + srcRegex + ", destFilePath="
                + destFilePath + ", locale=" + locale + ", templateFile=" + templateFile
                + ", proxyURL=" + proxyURL + ", connectTimeout=" + connectTimeout
                + ", requestTimeout=" + requestTimeout + ", retryCount=" + retryCount
                + ", retryInterval=" + retryInterval + ", isAutoSplitMode="
                + isAutoSplitMode + "]";
    }
}