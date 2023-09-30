package pro.kensait.brain2doc.process;

import static pro.kensait.brain2doc.common.ConsoleColor.*;

public class ConsoleProgressTask implements Runnable {
    private static final String DONE_BAR = "=> ";
    private static final String BAR = "=";
    private static final int INTERVAL = 3000;
    private static final int DELAY = 1500;
    private boolean isDone;

    public ConsoleProgressTask(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public void run() {
        sleepAWhile(DELAY);
        while (! isDone) {
            System.out.print(BAR);
            sleepAWhile(INTERVAL);
        }
        System.out.print(DONE_BAR + ANSI_BLUE + "done!" + ANSI_RESET);
    }

    private void sleepAWhile(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ie) {
            throw new RuntimeException(ie);
        }
    }
}