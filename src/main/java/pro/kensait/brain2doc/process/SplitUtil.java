package pro.kensait.brain2doc.process;

import java.util.ArrayList;
import java.util.List;

public class SplitUtil {
    private static final double MARKUP_FOR_SPLIT_COUNT = 0.5d;

    public static List<List<String>> split(List<String> requestLines, int splitCount) {
        // 10行のリストを3分割するのであれば、10÷3＝3.3
        // → intにキャストすると3になり、これに1を加えた数値が各リストの最大行数
        int maxLineSize = (requestLines.size() / splitCount) + 1;

        List<List<String>> splittedInputFileLists = new ArrayList<>();
        for (int i = 0; i < requestLines.size(); i += maxLineSize) {
            splittedInputFileLists.add(new ArrayList<>(
                    requestLines.subList(i,
                            Math.min(i + maxLineSize, requestLines.size()))));
        }
        return splittedInputFileLists;
    }
    
    public static int calcSplitCount(double tokenCount, double tokenCountLimit) {
        // トークン数比率と文字列比率が同じであるとは限らない、
        // またテンプレート追加なども考慮して、安全に倒すためマークアップを加算し、さらに切り上げる
        int splitCount = (int) (Math.ceil((tokenCount / tokenCountLimit) +
                MARKUP_FOR_SPLIT_COUNT));
        // TODO System.out.println("splitCount" + splitCount);
        return splitCount;
    }
}