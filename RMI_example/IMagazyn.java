package RMI_example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IMagazyn extends Remote {
    void dodajProdukt(String nazwa, int liczba) throws RemoteException;

    boolean usunProdukt(String nazwa) throws RemoteException;

    Map<String, Integer> pobierzProdukty() throws RemoteException;
}
