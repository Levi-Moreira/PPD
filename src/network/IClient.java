package network;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by ellca on 29/04/2017.
 */
public interface IClient extends Remote {


    void receiveChatMessage(IClient sender, String msg) throws RemoteException;

    String getName() throws RemoteException;

    void acceptGameStart(IClient senderClient, int pieceColour) throws RemoteException;

    void signalNotEnoughClients() throws RemoteException;

    void allEntered() throws RemoteException;

    void signalFullRoom() throws RemoteException;

    void assignPlayerNumber(int size) throws RemoteException;

    void serverLeft() throws RemoteException;

    void startTurn() throws RemoteException;

    void acceptAddToSpace(int space, int playerNumber) throws RemoteException;

    void performMove(int start, int end, int playerNumber) throws RemoteException;

    void makeCapture(int capturedPos) throws RemoteException;

    void anouncePartnerLeft(String clientName) throws RemoteException;

    void requestedRestart(String partnerName) throws RemoteException;

    void receiveAcceptRestart() throws RemoteException;

    void won() throws RemoteException;

    void removeSpace(int removePos) throws RemoteException;

    void askFinishGame(int piecesOnBoard) throws RemoteException;

    void actOnAnnounceTie() throws RemoteException;

    void actOnAnnounceNoTie() throws RemoteException;

    void lost() throws RemoteException;
}
