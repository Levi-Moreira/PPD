package network;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by ellca on 29/04/2017.
 */
public interface IServer extends Remote {


    void deliverChatMessage(IClient senderClient, String msg) throws RemoteException;

    void registerClient(IClient client) throws RemoteException;

    void disconnectClient(IClient client) throws RemoteException;

    void deliverGameStart(IClient client, int pieceColour) throws RemoteException;


}
