package pro.kensait.brain2doc.walk_read;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import pro.kensait.brain2doc.openai.ApiClient;
import pro.kensait.brain2doc.params.ResourceType;

public class ZipFileWalker {
    public static void processZipFile(Path srcPath, ResourceType resourceType) {
        ZipInputStream zis = null;
        try {
            zis = new ZipInputStream(new FileInputStream(srcPath.toFile()));
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                System.out.println("===== " + entryName + " =====");
                if (resourceType.matchesExt(entryName)) {
                    try (BufferedReader br =
                            new BufferedReader(new InputStreamReader(zis))) {
                        List<String> requestLines = new ArrayList<>();
                        String line;
                        while ((line = br.readLine()) != null) {
                            requestLines.add(line);
                        }
                        ApiClient.askToOpenAI(requestLines);
                    }
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
}