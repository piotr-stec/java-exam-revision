package producent_konsument.zad2_c;

public class Bufor {
    private final int ROZMIAR_BUFORA;
    private int[] bufor;
    private int indeksPobierania = 0;
    private int indeksWstawiania = 0;
    private int liczbaElementow = 0;

    public Bufor(int rozmiar) {
        ROZMIAR_BUFORA = rozmiar;
        bufor = new int[ROZMIAR_BUFORA];

    }

    public synchronized void wstaw(int liczba) throws InterruptedException {
        while (liczbaElementow == ROZMIAR_BUFORA) {
            wait();
        }
        bufor[indeksWstawiania] = liczba;
        indeksWstawiania = (indeksWstawiania + 1) % ROZMIAR_BUFORA;
        liczbaElementow++;
        notifyAll();

    }

    public synchronized int pobierz() throws InterruptedException {
        while (liczbaElementow == 0) {
            wait();
        }
        int liczba = bufor[indeksPobierania];
        indeksPobierania = (indeksPobierania + 1) % ROZMIAR_BUFORA;
        liczbaElementow--;
        notifyAll();
        return liczba;


    }

}
