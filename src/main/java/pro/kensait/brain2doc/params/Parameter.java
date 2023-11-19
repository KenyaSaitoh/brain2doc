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
    private GenerateType generateType; // デフォルト値はプロパティファイルから
    private String genTable = null; // 任意指定
    private String fields = null; // 任意指定
    private OutputScaleType outputScaleType; // デフォルト値はプロパティファイルから
    private Path srcPath; // 指定必須
    private String srcRegex; // 任意指定
    private Path destFilePath; // デフォルト値はソースパスと同じディレクトリの固定ファイル名
    private Locale locale; // デフォルト値はプロパティファイルから
    private Path templateFile; // 任意指定
    private int maxSplitCount;
    private String proxyURL; // 任意指定
    private int connectTimeout; // デフォルト値はプロパティファイルから
    private int requestTimeout; // デフォルト値はプロパティファイルから
    private int retryCount; // デフォルト値はプロパティファイルから
    private int retryInterval; // デフォルト値はプロパティファイルから
    private boolean isAutoSplitMode; // デフォルト値false
    private float temparature;
    private String charset; // デフォルト値はプロパティファイルから
    private boolean printPrompt;

    synchronized public static void setUp(String[] args) {
        // ローカル変数とデフォルト値の設定
        String openaiUrl = DefaultValueHolder.getProperty("openai_url");
        String openaiModel = DefaultValueHolder.getProperty("openai_model");
        String openaiApiKey = System.getenv(OPENAI_API_KEY);
        // TODO System.out.println("###" + openaiApiKey);
        ResourceType resourceType = ResourceType.valueOf(
                DefaultValueHolder.getProperty("resource").toUpperCase());
        GenerateType generateType = null;
        String genTable = null;
        String fields = null;
        OutputScaleType outputScaleType = OutputScaleType.valueOf(
                DefaultValueHolder.getProperty("output_scale").toUpperCase());
        String srcRegex = null;
        String destParam = null;
        String langParam = DefaultValueHolder.getProperty("lang");
        String templateFileParam = null;
        int connectTimeout = Integer.parseInt(
                DefaultValueHolder.getProperty("connect_timeout"));
        String proxyURL = null;
        int maxSplitCount = Integer.parseInt(
                DefaultValueHolder.getProperty("max_split_count"));
        int requestTimeout = Integer.parseInt(
                DefaultValueHolder.getProperty("timeout"));
        int retryCount = Integer.parseInt(
                DefaultValueHolder.getProperty("retry_count"));
        int retryInterval = Integer.parseInt(
                DefaultValueHolder.getProperty("retry_interval"));
        boolean isAutoSplitMode = false;
        float temparature = Float.parseFloat(
                DefaultValueHolder.getProperty("temparature"));
        String charset = DefaultValueHolder.getProperty("charset");

        // ソースパスの設定
        String srcParam = args[0];
        if (srcParam == null || srcParam.isEmpty() || srcParam.startsWith("-"))
            throw new IllegalArgumentException("ソースが指定されていません");

        // 引数として指定されたパラメータの取得と設定
        try {
            for (int i = 1; i < args.length; i++) {
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
                        resourceType = ResourceType
                                .getResourceTypeByName(args[++i].toLowerCase());
                        if (resourceType == null) {
                            resourceType = ResourceType.OTHERS;
                        }
                    } else if (args[i].equalsIgnoreCase("--gen")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        generateType = GenerateType
                                .getGenerateTypeByName(args[++i].toLowerCase());
                    } else if (args[i].equalsIgnoreCase("--gen-table")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        genTable = args[++i];
                    } else if (args[i].equalsIgnoreCase("--fields")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        fields = args[++i];
                    } else if (args[i].equalsIgnoreCase("--output-scale")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        outputScaleType = OutputScaleType
                                .getOutputScaleTypeByName(args[++i].toLowerCase());
                        if (outputScaleType == null)
                            throw new IllegalArgumentException();
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
                    } else if (args[i].equalsIgnoreCase("--max-split-count")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        maxSplitCount = Integer.parseInt(args[++i]);
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
                    } else if (args[i].equalsIgnoreCase("--temparature")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        temparature = Float.parseFloat(args[++i]);
                    } else if (args[i].equalsIgnoreCase("--charset")) {
                        if (args[i + 1].startsWith("-"))
                            continue;
                        charset = args[++i];
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

        // 生成種別のチェック
        if (generateType == null) {
            if (genTable == null || genTable.isEmpty()) {
                generateType = GenerateType.valueOf(
                        DefaultValueHolder.getProperty("generate").toUpperCase());
            } else {
                if (fields == null || fields.isEmpty()) {
                    throw new IllegalArgumentException("テーブルのフィールド名が指定されていません");
                }
            }
        } else {
            if (genTable != null && ! genTable.isEmpty()) {
                throw new IllegalArgumentException("\"gen\"と\"gen-table\"を同時に指定することはできません");
            }
        }

        // 入力元パスをチェックし、パラメータから入力元パスと入力元ディレクトリを決める
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
        if (destParam == null || destParam.isEmpty()) {
            // destオプションの指定がなかった場合は、destPathはソースパス＋デフォルト名
            destPath = Paths.get(srcDirPath.toString(),
                    getDefaultOutputFileName(resourceType, generateType));
        } else {
            // destオプションの指定があった場合は、ディレクトリ指定だった場合は
            // デフォルトファイル名を採用し、ファイル指定だった場合はそのまま
            destPath = Paths.get(destParam);
            if (Files.isDirectory(destPath)) {
                destPath = Paths.get(destPath.toString(),
                        getDefaultOutputFileName(resourceType, generateType));
            }
        }

        // ロケールを決める
        Locale locale = new Locale(langParam);

        // 外部指定されたテンプレートファイルを決める
        Path templateFile = templateFileParam != null ? Paths.get(templateFileParam)
                : null;

        parameter = new Parameter(openaiUrl, openaiModel, openaiApiKey,
                resourceType, generateType, genTable, fields,
                outputScaleType,
                srcPath, srcRegex, destPath,
                locale, templateFile,
                maxSplitCount,
                proxyURL, connectTimeout, requestTimeout, retryCount, retryInterval,
                isAutoSplitMode, temparature, charset, true);
    }

    private static String getDefaultOutputFileName(ResourceType resourceType,
            GenerateType generateType) {
        return DefaultValueHolder.getProperty("output_file_name") + "-" +
                resourceType.getName() + "-" +
                (generateType != null ? generateType.getName() : "table") + "-" +
                getCurrentDateTimeStr() +
                Const.OUTPUT_FILE_EXT;
    }

    private static String getCurrentDateTimeStr() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yMMddHHmmss");
        return localDateTime.format(dateTimeFormatter);
    }

    private Parameter(String openaiURL, String openaiModel, String openaiApikey,
            ResourceType resourceType, GenerateType generateType,
            String genTableName, String fieldsName,
            OutputScaleType outputScaleType,
            Path srcPath, String srcRegex, Path destFilePath,
            Locale locale, Path templateFile,
            int maxSplitCount,
            String proxyURL, int connectTimeout, int requestTimeout,
            int retryCount, int retryInterval,
            boolean isAutoSplitMode,
            float temparature,
            String charset,
            boolean printPrompt) {
        this.openaiURL = openaiURL;
        this.openaiModel = openaiModel;
        this.openaiApikey = openaiApikey;
        this.resourceType = resourceType;
        this.generateType = generateType;
        this.genTable = genTableName;
        this.fields = fieldsName;
        this.outputScaleType = outputScaleType;
        this.srcPath = srcPath;
        this.srcRegex = srcRegex;
        this.destFilePath = destFilePath;
        this.locale = locale;
        this.templateFile = templateFile;
        this.maxSplitCount = maxSplitCount;
        this.proxyURL = proxyURL;
        this.connectTimeout = connectTimeout;
        this.requestTimeout = requestTimeout;
        this.retryCount = retryCount;
        this.retryInterval = retryInterval;
        this.isAutoSplitMode = isAutoSplitMode;
        this.temparature = temparature;
        this.charset = charset;
        this.printPrompt = printPrompt;
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

    public GenerateType getGenerateType() {
        return generateType;
    }

    public String getGenTable() {
        return genTable;
    }

    public String getFields() {
        return fields;
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

    public int getMaxSplitCount() {
        return maxSplitCount;
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

    public float getTemparature() {
        return temparature;
    }

    public static String getOpenaiApiKey() {
        return OPENAI_API_KEY;
    }

    public String getCharset() {
        return charset;
    }

    public boolean isPrintPrompt() {
        return printPrompt;
    }

    public void setPrintPrompt(boolean printPrompt) {
        this.printPrompt = printPrompt;
    }

    @Override
    public String toString() {
        return "Parameter [openaiURL=" + openaiURL + ", openaiModel=" + openaiModel
                + ", openaiApikey=" + openaiApikey + ", resourceType=" + resourceType
                + ", generateType=" + generateType + ", genTable=" + genTable
                + ", fields=" + fields + ", outputScaleType=" + outputScaleType
                + ", srcPath=" + srcPath + ", srcRegex=" + srcRegex + ", destFilePath="
                + destFilePath + ", locale=" + locale + ", templateFile=" + templateFile
                + ", maxSplitCount=" + maxSplitCount + ", proxyURL=" + proxyURL
                + ", connectTimeout=" + connectTimeout + ", requestTimeout="
                + requestTimeout + ", retryCount=" + retryCount + ", retryInterval="
                + retryInterval + ", isAutoSplitMode=" + isAutoSplitMode
                + ", temparature=" + temparature + ", charset=" + charset
                + ", printPrompt=" + printPrompt + "]";
    }
}