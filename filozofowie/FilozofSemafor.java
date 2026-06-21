package filozofowie;

import java.util.concurrent.Semaphore;

public class FilozofSemafor extends Thread {

    static final int MAX = 5;
    static Semaphore[] widelec = new Semaphore[MAX];
    static Semaphore jadalnia = new Semaphore(MAX - 1); // ogranicza liczbę filozofów sięgających po widelce

    int mojNum;

    public FilozofSemafor(int nr) {
        mojNum = nr;
    }

    public void run() {
        while (true) {

            try {
                Thread.sleep((long) (1000 * Math.random())); // myślenie
            } catch (InterruptedException e) {
            }

            jadalnia.acquireUninterruptibly(); // wejście do "jadalni" — limit MAX-1 naraz

            widelec[mojNum].acquireUninterruptibly();
            widelec[(mojNum + 1) % MAX].acquireUninterruptibly();

            System.out.println("Zaczyna jeść " + mojNum);
            try {
                Thread.sleep((long) (500 * Math.random())); // jedzenie
            } catch (InterruptedException e) {
            }
            System.out.println("Kończy jeść " + mojNum);

            widelec[mojNum].release();
            widelec[(mojNum + 1) % MAX].release();

            jadalnia.release();
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < MAX; i++) {
            widelec[i] = new Semaphore(1);
        }

        for (int i = 0; i < MAX; i++) {
            (new FilozofSemafor(i)).start();
        }
    }
}