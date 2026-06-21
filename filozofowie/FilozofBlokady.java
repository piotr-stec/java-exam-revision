package filozofowie;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FilozofBlokady extends Thread {

    static final int MAX = 5;
    static Lock[] widelec = new Lock[MAX];
    static LimitJadalni jadalnia = new LimitJadalni(MAX - 1); // licznik na bazie Lock/Condition

    int mojNum;

    public FilozofBlokady(int nr) {
        mojNum = nr;
    }

    public void run() {
        while (true) {

            try {
                Thread.sleep((long) (1000 * Math.random())); // myślenie
            } catch (InterruptedException e) {
            }

            try {
                jadalnia.wejdz();

                widelec[mojNum].lock();
                widelec[(mojNum + 1) % MAX].lock();

                System.out.println("Zaczyna jeść " + mojNum);
                Thread.sleep((long) (500 * Math.random())); // jedzenie
                System.out.println("Kończy jeść " + mojNum);

            } catch (InterruptedException e) {
            } finally {
                widelec[mojNum].unlock();
                widelec[(mojNum + 1) % MAX].unlock();
                jadalnia.wyjdz();
            }
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < MAX; i++) {
            widelec[i] = new ReentrantLock();
        }

        for (int i = 0; i < MAX; i++) {
            (new FilozofBlokady(i)).start();
        }
    }
}

// Licznik wątków mogących jednocześnie "wejść do jadalni" — zbudowany na Lock/Condition
class LimitJadalni {

    private final Lock lock = new ReentrantLock();
    private final Condition warunek = lock.newCondition();
    private int wolneMiejsca;

    public LimitJadalni(int limit) {
        this.wolneMiejsca = limit;
    }

    public void wejdz() throws InterruptedException {
        lock.lock();
        try {
            while (wolneMiejsca <= 0) {
                warunek.await();
            }
            wolneMiejsca--;
        } finally {
            lock.unlock();
        }
    }

    public void wyjdz() {
        lock.lock();
        try {
            wolneMiejsca++;
            warunek.signal();
        } finally {
            lock.unlock();
        }
    }
}