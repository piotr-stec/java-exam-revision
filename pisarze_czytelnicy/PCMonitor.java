package pisarze_czytelnicy;

public class PCMonitor {

    private int liczCzyt = 0;
    private int liczPisa = 0;
    private boolean ktosPisze = false;

    public synchronized void chcePisac() {
        liczPisa++;
    }

    public synchronized void startCzytania() {
        while (liczPisa != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        liczCzyt++;
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
        notifyAll();
    }
}