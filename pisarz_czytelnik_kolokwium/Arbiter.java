package pisarz_czytelnik_kolokwium;

public class Arbiter {

    private int liczCzyt = 0;
    private int liczPisa = 0;          // liczba oczekujących/zgłoszonych pisarzy
    private boolean ktosPisze = false;
    private int liczbaPisarzyZRzedu = 0; // ilu pisarzy "pod rząd" już napisało

    private static final int MAX_PISARZY_Z_RZEDU = 2;

    public synchronized void chcePisac() {
        liczPisa++;
    }

    public synchronized void startCzytania() {
        // czytelnik czeka, jeśli ktoś pisze, albo są oczekujący pisarze
        // i limit pisarzy "pod rząd" jeszcze nie został osiągnięty
        while (ktosPisze || (liczPisa != 0 && liczbaPisarzyZRzedu < MAX_PISARZY_Z_RZEDU)) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        liczCzyt++;
        liczbaPisarzyZRzedu = 0; // czytelnik wszedł -> resetujemy licznik
    }

    public synchronized void stopCzytania() {
        liczCzyt--;
        notifyAll();
    }

    public synchronized void startPisania() {
        while ((liczCzyt != 0) || ktosPisze) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        ktosPisze = true;
    }

    public synchronized void stopPisania() {
        ktosPisze = false;
        liczPisa--;
        liczbaPisarzyZRzedu++;
        notifyAll();
    }
}