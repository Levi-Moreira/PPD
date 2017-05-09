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

    void clientEndedTurn(IClient senderClient) throws RemoteException;

    void requestAddToSpace(IClient senderClient, int space, int playerNumber) throws RemoteException;

    void requestPerformMove(IClient senderClient, int start, int end, int playerNumber) throws RemoteException;

    void askPerformCapture(IClient senderClient, int capturedPos) throws RemoteException;

    void terminateClient(IClient senderClient, String clientName) throws RemoteException;

    void deliverAskForRestart(IClient senderClient, String partnerName) throws RemoteException;

    void deliverAcceptRestart(IClient senderClient) throws RemoteException;

    void clientWon(IClient senderClient) throws RemoteException;

    void performRemoval(IClient senderClient, int removePos) throws RemoteException;

    void finishGame(IClient senderClient, int piecesOnBoard) throws RemoteException;

    void deliverAnounceTie(IClient senderClient) throws RemoteException;

    void deliverAnounceNoTie(IClient senderClient) throws RemoteException;

    void deliverAnounceLost(IClient senderClient) throws RemoteException;
}
