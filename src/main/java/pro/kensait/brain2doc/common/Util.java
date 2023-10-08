package pro.kensait.brain2doc.common;

public class Util {
    public  static void sleepAWhile(int retryInterval) {
        try {
            Thread.sleep(retryInterval * 1000);
        } catch(InterruptedException ie) {
            throw new RuntimeException(ie);
        }
    }
}