package network;

import model.io.ServerNetworkModel;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * The server class for the yote game
 */
public class Server extends UnicastRemoteObject implements IServer {

    //holds the clients
    private static ArrayList<IClient> clients;


    private ServerNetworkModel model;


    //constructor
    public Server(ServerNetworkModel model) throws RemoteException {
        super();

        this.model = model;
        clients = new ArrayList<>();
    }


    /**
     * Closes the server and warn the clients of this
     *
     * @throws IOException
     */
    public void exit() throws RemoteException {

        if (clients.size() == 1) {
            clients.get(0).serverLeft();

        }

        if (clients.size() == 2) {
            clients.get(0).serverLeft();
            clients.get(1).serverLeft();
        }

        System.exit(0);
    }


    @Override
    public void deliverChatMessage(IClient senderClient, String msg) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.receiveChatMessage(senderClient, msg);
        }
    }

    @Override
    public void registerClient(IClient client) throws RemoteException {

        if (clients.size() < 2) {
            clients.add(client);
            client.assignPlayerNumber(clients.size());
            if (clients.size() == 2) {
                clients.get(0).allEntered();
                clients.get(1).allEntered();
            }
        } else {
            client.signalFullRoom();
        }
    }

    @Override
    public void disconnectClient(IClient client) throws RemoteException {
        clients.remove(client);
    }

    @Override
    public void deliverGameStart(IClient senderClient, int pieceColour) throws RemoteException {

        if (clients.size() < 2) {
            senderClient.signalNotEnoughClients();
        } else {
            for (IClient client : clients) {
                if (!client.equals(senderClient))
                    client.acceptGameStart(senderClient, pieceColour);
            }

        }

    }

    @Override
    public void clientEndedTurn(IClient senderClient) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.startTurn();
        }
    }

    @Override
    public void requestAddToSpace(IClient senderClient, int space, int playerNumber) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.acceptAddToSpace(space, playerNumber);
        }
    }

    @Override
    public void requestPerformMove(IClient senderClient, int start, int end, int playerNumber) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.performMove(start, end, playerNumber);
        }
    }

    @Override
    public void askPerformCapture(IClient senderClient, int capturedPos) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.makeCapture(capturedPos);
        }
    }

    @Override
    public void terminateClient(IClient senderClient, String clientName) throws RemoteException {
        clients.remove(senderClient);
        clients.get(0).anouncePartnerLeft(clientName);
    }

    @Override
    public void deliverAskForRestart(IClient senderClient, String partnerName) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.requestedRestart(partnerName);
        }
    }

    @Override
    public void deliverAcceptRestart(IClient senderClient) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.receiveAcceptRestart();
        }
    }

    @Override
    public void clientWon(IClient senderClient) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.won();
        }
    }

    @Override
    public void performRemoval(IClient senderClient, int removePos) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.removeSpace(removePos);
        }
    }

    @Override
    public void finishGame(IClient senderClient, int piecesOnBoard) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.askFinishGame(piecesOnBoard);
        }
    }

    @Override
    public void deliverAnounceTie(IClient senderClient) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.actOnAnnounceTie();
        }
    }

    @Override
    public void deliverAnounceNoTie(IClient senderClient) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.actOnAnnounceNoTie();
        }
    }

    @Override
    public void deliverAnounceLost(IClient senderClient) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient))
                client.lost();
        }
    }


}
