package inne_zadania.semafory;

public class SemaforZliczajacyMonitor {
    private int permits;

    public SemaforZliczajacyMonitor(int initialPermits) {
        this.permits = initialPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits <= 0) {
            wait();
        }
        permits--;
    }

    public synchronized void release() {
        permits++;
        notifyAll();
    }
}
