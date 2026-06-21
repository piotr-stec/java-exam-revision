package inne_zadania.semafory;

public class SemaforBinarnyMonitor {
    private boolean available;

    public SemaforBinarnyMonitor(boolean initial) {
        this.available = initial;
    }

    public synchronized void acquire() throws InterruptedException {
        while (!available) {
            wait();
        }
        available = false;
    }

    public synchronized void release() {
        if (!available) {
            available = true;
            notifyAll();
        }
    }
}
