package producent_konsument.zad2_c;

public class Konsument implements Runnable {
    Bufor bufor;

    public Konsument(Bufor bufor) {
        this.bufor = bufor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int liczba = bufor.pobierz();
                if (liczba % 2 == 0) {
                    ObsłużParzyste(liczba);
                } else {
                    ObsłużNieparzyste(liczba);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void ObsłużParzyste(int liczba) {
        System.out.println("Obsługuje parzystą: " + liczba);
    }

    public void ObsłużNieparzyste(int liczba) {
        System.out.println("Obsługuje nieparzyste: " + liczba);

    }

}
