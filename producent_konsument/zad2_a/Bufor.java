package producent_konsument.zad2_a;

import java.util.concurrent.Semaphore;

public class Bufor {
    private final int ROZMIAR_BUFORA;
    private int[] bufor;
    private int indeksPobierania = 0;
    private int indeksWstawiania = 0;
    private Semaphore mutex;
    private Semaphore doPobrania;
    private Semaphore wolneMiejsce;

    public Bufor(int rozmiar) {
        ROZMIAR_BUFORA = rozmiar;
        bufor = new int[ROZMIAR_BUFORA];
        mutex = new Semaphore(1);
        wolneMiejsce = new Semaphore(ROZMIAR_BUFORA);
        doPobrania = new Semaphore(0);
    }

    public void wstaw(int liczba) throws InterruptedException {
        wolneMiejsce.acquire();
        mutex.acquire();
        bufor[indeksWstawiania] = liczba;
        indeksWstawiania = (indeksWstawiania + 1) % ROZMIAR_BUFORA;
        mutex.release();
        doPobrania.release();
    }

    public int pobierz() throws InterruptedException {
        doPobrania.acquire();
        mutex.acquire();
        int liczba = bufor[indeksPobierania];
        indeksPobierania = (indeksPobierania + 1) % ROZMIAR_BUFORA;
        mutex.release();
        wolneMiejsce.release();
        return liczba;

    }

}
