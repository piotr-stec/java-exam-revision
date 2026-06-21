package pisarze_czytelnicy;

import java.util.concurrent.Semaphore;

public class PCSemafor {

    private int liczCzyt = 0;
    private int liczPisa = 0;

    private final Semaphore mutexCzyt = new Semaphore(1); // chroni liczCzyt
    private final Semaphore mutexPisa = new Semaphore(1); // chroni liczPisa
    private final Semaphore wpisz = new Semaphore(1); // wyłączność pisania
    private final Semaphore czytajPisz = new Semaphore(1); // blokuje czytelników, gdy są oczekujący pisarze

    public void chcePisac() throws InterruptedException {
        mutexPisa.acquire();
        liczPisa++;
        if (liczPisa == 1) {
            czytajPisz.acquire(); // pierwszy oczekujący pisarz blokuje nowych czytelników
        }
        mutexPisa.release();
    }

    public void startCzytania() throws InterruptedException {
        czytajPisz.acquire(); // czeka, jeśli jest oczekujący/piszący pisarz
        mutexCzyt.acquire();
        liczCzyt++;
        if (liczCzyt == 1) {
            wpisz.acquire(); // pierwszy czytelnik blokuje pisarzy
        }
        mutexCzyt.release();
        czytajPisz.release();
    }

    public void stopCzytania() throws InterruptedException {
        mutexCzyt.acquire();
        liczCzyt--;
        if (liczCzyt == 0) {
            wpisz.release(); // ostatni czytelnik odblokowuje pisarzy
        }
        mutexCzyt.release();
    }

    public void startPisania() throws InterruptedException {
        wpisz.acquire(); // wyłączność — nikt nie czyta i nikt nie pisze
    }

    public void stopPisania() throws InterruptedException {
        wpisz.release();
        mutexPisa.acquire();
        liczPisa--;
        if (liczPisa == 0) {
            czytajPisz.release(); // ostatni oczekujący/piszący pisarz odblokowuje czytelników
        }
        mutexPisa.release();
    }
}