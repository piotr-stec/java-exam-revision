package RMI_example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class MagazynImpl extends UnicastRemoteObject implements IMagazyn {

    private Map<String, Integer> produkty;

    public MagazynImpl() throws RemoteException {
        super();
        produkty = new HashMap<>();
    }

    @Override
    public synchronized void dodajProdukt(String nazwa, int liczba) throws RemoteException {
        if (produkty.containsKey(nazwa)) {
            produkty.put(nazwa, produkty.get(nazwa) + liczba);
        } else {
            produkty.put(nazwa, liczba);
        }
    }

    @Override
    public synchronized boolean usunProdukt(String nazwa) throws RemoteException {
        if (produkty.containsKey(nazwa)) {
            produkty.remove(nazwa);
            return true;
        }
        return false;
    }

    @Override
    public synchronized Map<String, Integer> pobierzProdukty() throws RemoteException {
        return new HashMap<>(produkty);
    }
}