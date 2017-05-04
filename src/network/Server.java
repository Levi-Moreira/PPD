package network;

import model.entities.Message;
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
    public void exit() throws IOException {


        if (clients.size() == 1) {
            sendBack(clients.get(0), Message.SERVER_LEFT);

        }

        if (clients.size() == 2) {
            sendBack(clients.get(0), Message.SERVER_LEFT);
            sendBack(clients.get(1), Message.SERVER_LEFT);
        }

        System.exit(0);
    }


    private void sendAll(IClient senderClient, String message) throws RemoteException {
        for (IClient client : clients) {
            if (!client.equals(senderClient)) ;
            //client.receiveMessage(message);
        }
    }


    private void sendBack(IClient senderClient, String message) throws RemoteException {
        String json = model.assembleServerMessage(message);
        //senderClient.receiveMessage(json);
    }


    public void receiveMessage(IClient senderClient, String msg) throws RemoteException {

        System.out.println(msg);

        //if a client tries to start a match without all users connected, warn him
        if (msg.contains(Message.START_MATCH) && clients.size() < 2) {
            sendBack(senderClient, Message.NOT_ENOUGH_CLIENTS);
        } else {
            //normally send message to other client
            sendAll(senderClient, msg);
        }
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


}
