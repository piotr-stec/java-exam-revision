package filozofowie;

public class FilozofMonitor extends Thread {
    private final int id;
    private final PrzydzialWidelcow arbiter;

    public FilozofMonitor(int id, PrzydzialWidelcow arbiter) {
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

                // 2. Filozof robi się głodny i próbuje podnieść widelce
                arbiter.podniesWidelce(id);

                // 3. Filozof je (sekcja krytyczna)
                System.out.println("Filozof " + id + " ZACZYNA jeść.");
                Thread.sleep((long) (Math.random() * 2000));
                System.out.println("Filozof " + id + " KOŃCZY jeść.");

                // 4. Filozof odkłada widelce na stół
                arbiter.polozWidelce(id);

            } catch (InterruptedException e) {
                System.out.println("Działanie filozofa " + id + " zostało przerwane.");
                break;
            }
        }
    }
}

class PrzydzialWidelcow {
    // Tablica przechowująca stan widelców. 
    // false = widelec wolny, true = widelec zajęty przez kogoś
    private boolean[] widelec = new boolean[5];

    // Słowo 'synchronized' gwarantuje, że do metody wejdzie tylko jeden filozof na raz
    public synchronized void podniesWidelce(int id) {
        int lewy = id;
        int prawy = (id + 1) % 5;

        // Dopóki któryś z potrzebnych widelców jest zajęty, filozof musi czekać
        while (widelec[lewy] || widelec[prawy]) {
            try {
                wait(); // Usypia wątek i ZWALNIA blokadę monitora dla innych
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Kiedy pętla while się skończy, mamy pewność, że OBA widelce są wolne.
        // Filozof podnosi je (atomowo zajmuje oba).
        widelec[lewy] = true;
        widelec[prawy] = true;
    }

    public synchronized void polozWidelce(int id) {
        int lewy = id;
        int prawy = (id + 1) % 5;

        // Filozof odkłada widelce (zwalnia zasoby)
        widelec[lewy] = false;
        widelec[prawy] = false;

        // Budzimy WSZYSTKICH uśpionych filozofów, aby sprawdzili, 
        // czy teraz mogą podnieść swoje widelce
        notifyAll();
    }
}