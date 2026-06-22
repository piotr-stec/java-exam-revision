package pisarze_czytelnicy_bez_priorytetu;

import java.util.concurrent.Semaphore;

public class PCSemafor {
    private int liczCzyt = 0;
    
    // Chroni zmienną liczCzyt
    private final Semaphore mutexCzyt = new Semaphore(1); 
    // Wyłączność dostępu do pisania (chroni sekcję krytyczną dla pisarzy i pierwszego czytelnika)
    private final Semaphore wpisz = new Semaphore(1);     

    public void startCzytania() throws InterruptedException {
        mutexCzyt.acquire();
        liczCzyt++;
        if (liczCzyt == 1) {
            wpisz.acquire(); // Pierwszy czytelnik blokuje pisarzy
        }
        mutexCzyt.release();
    }

    public void stopCzytania() throws InterruptedException {
        mutexCzyt.acquire();
        liczCzyt--;
        if (liczCzyt == 0) {
            wpisz.release(); // Ostatni czytelnik odblokowuje pisarzy
        }
        mutexCzyt.release();
    }

    public void startPisania() throws InterruptedException {
        wpisz.acquire(); // Czeka, aż nie będzie żadnych czytelników i żadnych innych pisarzy
    }

    public void stopPisania() {
        wpisz.release(); // Odblokowuje czekających czytelników lub innych pisarzy
    }
}
