package producent_konsument.zad2_c;

public class Producent implements Runnable {
    Bufor bufor;
    int nr;

    public Producent(Bufor bufor, int numer) {
        this.bufor = bufor;
        this.nr = numer;
    }

    @Override
    public void run() {
        int liczba = nr;
        System.out.println("Wątek nr: " + nr + " zaczyna pracę");
        while (true) {
            try {
                System.out.println("Czeka sleep");
                Thread.sleep((long) (Math.random() * 5000));
                System.out.println("koniec sleep");

                bufor.wstaw(liczba);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            liczba += nr;
        }
    }

}
