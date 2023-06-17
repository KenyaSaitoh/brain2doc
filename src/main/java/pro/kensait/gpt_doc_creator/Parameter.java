package pro.kensait.gpt_doc_creator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Parameter {
    private static final String DEFAULT_OUTPUT_FILE_NAME = "output.md";
    private String key;
    private Path srcPath;
    private Path destPath;

    public static Parameter of(String[] args) {
        String key = null;
        String srcPathStr = null;
        String destPathStr = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-k")) {
                if (args[i + 1].startsWith("-")) continue;
                key = args[++i];
            } else if (args[i].equalsIgnoreCase("-s")) {
                if (args[i + 1].startsWith("-")) continue;
                srcPathStr = args[++i];
            } else if (args[i].equalsIgnoreCase("-d")) {
                if (args[i + 1].startsWith("-")) continue;
                destPathStr = args[++i];
            } else {
                throw new IllegalArgumentException("パラメータエラー");
            }
        }
        if (key == null) throw new IllegalArgumentException("APIキーが指定されていません");
        if (srcPathStr == null) throw new IllegalArgumentException("ソースが指定されていません");
        Path srcPath = Paths.get(srcPathStr);
        Path destPath = null; 
        boolean isDirectory = Files.isDirectory(srcPath);
        if (destPathStr == null) {
            if (isDirectory) {
                destPath = Paths.get(srcPathStr, DEFAULT_OUTPUT_FILE_NAME);
            } else {
                Path parent = srcPath.getParent();
                destPath = Paths.get(parent.toString(), DEFAULT_OUTPUT_FILE_NAME);
            }
        }
        return new Parameter(key, srcPath, destPath);
    }

    public Parameter(String key, Path srcPath, Path destPath) {
        this.key = key;
        this.srcPath = srcPath;
        this.destPath = destPath;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Path getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(Path srcPath) {
        this.srcPath = srcPath;
    }

    public Path getDestPath() {
        return destPath;
    }

    public void setDestPath(Path destPath) {
        this.destPath = destPath;
    }

    @Override
    public String toString() {
        return "Parameter [key=" + key + ", srcPath=" + srcPath + ", destPath=" + destPath
                + "]";
    }
}