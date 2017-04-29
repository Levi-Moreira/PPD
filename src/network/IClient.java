package network;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by ellca on 29/04/2017.
 */
public interface IClient extends Remote{

    void receiveMessage(String msg) throws RemoteException;
}
