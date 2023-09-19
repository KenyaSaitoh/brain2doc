package pro.kensait.brain2doc.write;

public class JavaSpecOutputStrategy implements InputStrategy {
    private static final String PACKAGE_REGEX = "package (.*);";

    /*
    private void processJava(List<String> responseLines) throws Exception {
        System.out.println("\n##### " + inputFile.getFileName() + " #####");
        if (inputFile.toString().endsWith(".java")) {
        String className = inputFile.getFileName().toString()
                .replaceAll("(.*)\\.java", "$1") + "クラス";
        List<String> inputFileLines = Files.readAllLines(inputFile);
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
    */
}