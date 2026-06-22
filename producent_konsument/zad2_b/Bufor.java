package producent_konsument.zad2_b;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bufor {
    private final int ROZMIAR_BUFORA;
    private int[] bufor;
    private int indeksPobierania = 0;
    private int indeksWstawiania = 0;
    private int liczbaElementow = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition wolneMiejsce = lock.newCondition();
    private final Condition doPobrania = lock.newCondition();

    public Bufor(int rozmiar) {
        ROZMIAR_BUFORA = rozmiar;
        bufor = new int[ROZMIAR_BUFORA];
    }

    public void wstaw(int liczba) throws InterruptedException {
        lock.lock();
        try {
            while (liczbaElementow == ROZMIAR_BUFORA) {
                wolneMiejsce.await();
            }
            bufor[indeksWstawiania] = liczba;
            indeksWstawiania = (indeksWstawiania + 1) % ROZMIAR_BUFORA;
            liczbaElementow++;
            doPobrania.signal();
        } finally {
            lock.unlock();
        }

    }

    public int pobierz() throws InterruptedException {
        lock.lock();
        try {
            while (liczbaElementow == 0) {
                doPobrania.await();
            }
            int liczba = bufor[indeksPobierania];
            indeksPobierania = (indeksPobierania + 1) % ROZMIAR_BUFORA;
            liczbaElementow--;
            wolneMiejsce.signal();
            return liczba;

        } finally {
            lock.unlock();
        }

    }

}
