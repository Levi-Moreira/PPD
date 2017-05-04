package network;

import model.io.ClientNetworkModel;

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


    void clientEndedTurn(IClient senderClient) throws RemoteException;

    void requestAddToSpace(IClient senderClient, int space, int playerNumber) throws RemoteException;

    void requestPerformMove(IClient senderClient, int start, int end, int playerNumber) throws RemoteException;
}
