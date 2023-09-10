package jp.mufg.it.openai.docgen.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import jp.mufg.it.openai.docgen.params.Parameter;

public class FileReader {
    
    private static void processDirectory(Parameter params) throws IOException {
        Files.walkFileTree(params.getSrcPath(), new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path inputFile, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println("#####" + inputFile.getFileName() + "#####");
                if (Files.isDirectory(inputFile)) return FileVisitResult.CONTINUE;
                if (inputFile.toString().endsWith(".java")) {
                    List<String> content = Files.readAllLines(inputFile);
                    //askToChatGPT(content, params);
                }
                return FileVisitResult.CONTINUE;
            };
        });
    }

    private static String getLinesAsString(List<String> content) {
        StringBuilder sb = new StringBuilder();
        sb.append("日本語でお願いします Javaのソースコードです" + System.getProperty("line.separator"));
        for (String line : content) {
            sb.append(line + System.getProperty("line.separator"));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
    
    private static void processZipFile(Parameter params) {
        try (BufferedReader br = Files.newBufferedReader(params.getSrcPath());
                BufferedWriter bw = Files.newBufferedWriter(params.getDestPath())) {
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line + System.lineSeparator());
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        
        /*
        try () {
            // ZIPデータの出力先をファイル出力ストリームを用いて指定
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
            // ZIPエントリ対象ファイル分ループし、ZIPエントリを生成
            for (int i = 1; i < args.length; i++) {
                String entryName = args[i];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(entryName));
                ZipEntry entry = new ZipEntry(entryName);
                // 対象のZIPエントリの開始位置にZIP出力ストリームを配置
                zos.putNextEntry(entry);
                // 128バイトずつ読み込んでは出力ストリームに書き込み
                byte[] buf = new byte[128];
                int size;
                while ((size = bis.read(buf, 0, buf.length)) != -1) {
                    zos.write(buf, 0, size);
                }
                // ZIPエントリへの出力とクローズ
                zos.flush();
                zos.closeEntry();
                bis.close();
            }
            // ストリームのクローズ
            zos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        */
    }
}