package inne_zadania.semafory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaforZliczającyBlokada {
    private int permits;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public SemaforZliczającyBlokada(int initialPermits) {
        this.permits = initialPermits;
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits <= 0) {
                condition.await();
            }
            permits--;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            permits++;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
