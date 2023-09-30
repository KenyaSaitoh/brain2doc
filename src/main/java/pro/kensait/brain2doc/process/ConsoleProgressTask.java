package pro.kensait.brain2doc.process;

import static pro.kensait.brain2doc.common.ConsoleColor.*;

import java.util.concurrent.CountDownLatch;

public class ConsoleProgressTask implements Runnable {
    private static final String DONE_BAR = "=> ";
    private static final String BAR = "=";
    private static final int INTERVAL = 2000;
    private CountDownLatch startSignal;
    private boolean isDone;

    public ConsoleProgressTask(CountDownLatch startSignal, boolean isDone) {
        this.startSignal = startSignal;
        this.isDone = isDone;
    }

    public CountDownLatch getStartSignal() {
        return startSignal;
    }

    public void setStartSignal(CountDownLatch startSignal) {
        this.startSignal = startSignal;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            while (! isDone) {
                System.out.print(BAR);
                sleepAWhile(INTERVAL);
            }
            System.out.println(DONE_BAR + ANSI_BLUE + "done!" + ANSI_RESET);
        } catch (InterruptedException ie) {
            throw new RuntimeException(ie);
        }
    }

    private void sleepAWhile(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ie) {
            throw new RuntimeException(ie);
        }
    }
}