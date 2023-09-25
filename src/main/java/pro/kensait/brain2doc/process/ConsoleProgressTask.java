package pro.kensait.brain2doc.process;

import static pro.kensait.brain2doc.common.ConsoleColor.*;

public class ConsoleProgressTask implements Runnable {
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
        while (! isDone) {
            System.out.print(".");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }
        System.out.print(ANSI_BLUE + "done!" + ANSI_RESET);
    }
}