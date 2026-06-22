package pisarze_czytelnicy_bez_priorytetu;

public class PCMonitor {
    private int liczCzyt = 0;
    private boolean ktosPisze = false;

    public synchronized void startCzytania() {
        // Czytelnicy czekają tylko wtedy, gdy ktoś faktycznie pisze
        while (ktosPisze) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        liczCzyt++;
    }

    public synchronized void stopCzytania() {
        liczCzyt--;
        if (liczCzyt == 0) {
            notifyAll(); // Wybudza oczekujących pisarzy, jeśli to był ostatni czytelnik
        }
    }

    public synchronized void startPisania() {
        // Pisarz czeka, jeśli ktoś czyta lub ktoś inny pisze
        while (liczCzyt > 0 || ktosPisze) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        ktosPisze = true;
    }

    public synchronized void stopPisania() {
        ktosPisze = false;
        notifyAll(); // Wybudza wszystkich: i czytelników, i innych pisarzy
    }
}
