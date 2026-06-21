package RMI_example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
// import MagazynImpl;
public class Server {
    public static void main(String[] args){
        try{
        MagazynImpl magazyn = new MagazynImpl();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("Magazyn", magazyn);} catch(Exception e){
            System.err.println("Błąd: "+e);
            e.printStackTrace();
        }
    }

}
