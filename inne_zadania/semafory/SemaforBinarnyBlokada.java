package inne_zadania.semafory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaforBinarnyBlokada {
    private boolean available;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public SemaforBinarnyBlokada(boolean initial) {
        this.available = initial;
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (!available) {
                condition.await();
            }
            available = false;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            if (!available) {
                available = true;
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
