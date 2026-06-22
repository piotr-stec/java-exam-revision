package pisarz_czytelnik_kolokwium;


public class Czytelnik extends Thread {

    private Arbiter a;
    private int nr;

    public Czytelnik(Arbiter a, int nr) {
        this.a = a;
        this.nr = nr;
    }

    public void run() {
        while (true) {
            a.startCzytania();
            System.out.println("Start CZYTANIA " + nr);
            // czytanie ...
            System.out.println("Stop CZYTANIA " + nr);
            a.stopCzytania();
        }
    }
}