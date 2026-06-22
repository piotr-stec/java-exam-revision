package pisarz_czytelnik_kolokwium;

public class Pisarz extends Thread {

    private Arbiter a;
    private int nr;

    public Pisarz(Arbiter a, int nr) {
        this.a = a;
        this.nr = nr;
    }

    public void run() {
        while (true) {
            a.chcePisac();
            a.startPisania();
            System.out.println("Start PISANIA " + nr);
            // pisanie ...
            System.out.println("Stop PISANIA " + nr);
            a.stopPisania();
        }
    }
}