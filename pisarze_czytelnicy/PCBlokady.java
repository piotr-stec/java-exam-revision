package pisarze_czytelnicy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PCBlokady {

    private final Lock lock = new ReentrantLock();
    private final Condition warunek = lock.newCondition();

    private int liczCzyt = 0;
    private int liczPisa = 0;
    private boolean ktosPisze = false;

    public void chcePisac() {
        lock.lock();
        try {
            liczPisa++;
        } finally {
            lock.unlock();
        }
    }

    public void startCzytania() throws InterruptedException {
        lock.lock();
        try {
            while (liczPisa != 0) {
                warunek.await();
            }
            liczCzyt++;
        } finally {
            lock.unlock();
        }
    }

    public void stopCzytania() {
        lock.lock();
        try {
            liczCzyt--;
            warunek.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void startPisania() throws InterruptedException {
        lock.lock();
        try {
            while ((liczCzyt != 0) || ktosPisze) {
                warunek.await();
            }
            ktosPisze = true;
        } finally {
            lock.unlock();
        }
    }

    public void stopPisania() {
        lock.lock();
        try {
            ktosPisze = false;
            liczPisa--;
            warunek.signalAll();
        } finally {
            lock.unlock();
        }
    }
}