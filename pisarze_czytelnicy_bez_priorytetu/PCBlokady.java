package pisarze_czytelnicy_bez_priorytetu;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PCBlokady {
    private int liczCzyt = 0;
    private boolean ktosPisze = false;
    private final Lock lock = new ReentrantLock();
    private final Condition moznaCzytac = lock.newCondition();
    private final Condition moznaPisac = lock.newCondition();

    public void startCzytania() throws InterruptedException {
        lock.lock();
        try {
            while (ktosPisze) {
                moznaCzytac.await();
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
            if (liczCzyt == 0) {
                moznaPisac.signal(); // Wybudza jednego pisarza
            }
        } finally {
            lock.unlock();
        }
    }

    public void startPisania() throws InterruptedException {
        lock.lock();
        try {
            while (liczCzyt > 0 || ktosPisze) {
                moznaPisac.await();
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
            moznaCzytac.signalAll(); // Preferujemy wybudzenie wszystkich czytelników
            moznaPisac.signal();     // Oraz jednego pisarza (sprawiedliwe/priorytet czytelników)
        } finally {
            lock.unlock();
        }
    }
}
