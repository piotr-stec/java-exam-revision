package filozofowie;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FilozofBlokadyArbiter extends Thread {
    private final int id;
    private final PrzydzialWidelcowBlokady arbiter;

    public FilozofBlokadyArbiter(int id, PrzydzialWidelcowBlokady arbiter) {
        this.id = id;
        this.arbiter = arbiter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 1. Filozof myśli
                System.out.println("Filozof " + id + " myśli...");
                Thread.sleep((long) (Math.random() * 3000));

                // 2. Filozof robi się głodny i prosi Kelnera (Arbitra) o dwa widelce naraz
                arbiter.podniesWidelce(id);

                // 3. Filozof je
                System.out.println("Filozof " + id + " ZACZYNA jeść.");
                Thread.sleep((long) (Math.random() * 2000));
                System.out.println("Filozof " + id + " KOŃCZY jeść.");

                // 4. Filozof oddaje oba widelce
                arbiter.polozWidelce(id);

            } catch (InterruptedException e) {
                System.out.println("Działanie filozofa " + id + " zostało przerwane.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        PrzydzialWidelcowBlokady arbiter = new PrzydzialWidelcowBlokady();
        for (int i = 0; i < 5; i++) {
            new FilozofBlokadyArbiter(i, arbiter).start();
        }
    }
}

class PrzydzialWidelcowBlokady {
    // Odzwierciedlenie fizycznych widelców (false = wolny, true = zajęty)
    private boolean[] widelec = new boolean[5];
    
    // Jedna globalna blokada zamiast słowa "synchronized"
    private final Lock lock = new ReentrantLock();
    // Jeden globalny dzwonek (odpowiednik wait() / notifyAll())
    private final Condition condition = lock.newCondition();

    public void podniesWidelce(int id) throws InterruptedException {
        int lewy = id;
        int prawy = (id + 1) % 5;

        lock.lock(); // Wejście do sekcji krytycznej
        try {
            // Dopóki którykolwiek z potrzebnych widelców jest zajęty, czekaj
            while (widelec[lewy] || widelec[prawy]) {
                condition.await(); // Usypia wątek i zwalnia blokadę 'lock' na ten czas
            }

            // Jeśli pętla while puściła, oba widelce są wolne - bierzemy oba na raz!
            widelec[lewy] = true;
            widelec[prawy] = true;
        } finally {
            lock.unlock(); // Wyjście z sekcji krytycznej
        }
    }

    public void polozWidelce(int id) {
        int lewy = id;
        int prawy = (id + 1) % 5;

        lock.lock();
        try {
            widelec[lewy] = false;
            widelec[prawy] = false;
            
            // Wybudzamy wszystkich śpiących filozofów (odpowiednik notifyAll())
            // Każdy z nich sprawdzi swój warunek w pętli while
            condition.signalAll(); 
        } finally {
            lock.unlock();
        }
    }
}
