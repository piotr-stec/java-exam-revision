package RMI_example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

public class Client {

    public static void main(String[] args) {
        try{
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        IMagazyn magazyn = (IMagazyn) registry.lookup("Magazyn");

        magazyn.dodajProdukt("Mleko", 10);
        magazyn.dodajProdukt("Chleb", 5);
        magazyn.dodajProdukt("Mleko", 3); // zwiększy liczbę Mleka do 13

        Map<String, Integer> produkty = magazyn.pobierzProdukty();
        System.out.println("Stan magazynu: " + produkty);

        boolean usunieto = magazyn.usunProdukt("Chleb");
        System.out.println("Usunięto Chleb: " + usunieto);

        boolean usunieto2 = magazyn.usunProdukt("Masło"); // nie istnieje
        System.out.println("Usunięto Masło: " + usunieto2);

        System.out.println("Stan magazynu po zmianach: " + magazyn.pobierzProdukty());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
