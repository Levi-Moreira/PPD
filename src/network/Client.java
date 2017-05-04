package network;

import model.io.ClientNetworkModel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Client class that generates the main communication hub
 */
public class Client  {
/*

    private final IServer server;
    private ClientNetworkModel model;


    //constructor for local connections
    public Client(IServer server, ClientNetworkModel model) throws RemoteException {
        super();

        this.model = model;
        this.server = server;


    }

    @Override
    public void receiveChatMessage(IClient sender, String msg) throws RemoteException {
        model.recieveChatMessage(sender, msg);
    }

    @Override
    public String getName() throws RemoteException {
        return model.getName();
    }

    @Override
    public void acceptGameStart(IClient senderClient, int pieceColour) {
        model.startGame(pieceColour);
    }

    @Override
    public void signalNotEnoughClients() {
        model.signalNotEnoughClients();
    }

    @Override
    public void allEntered() throws RemoteException {
        model.signalAllEntered();
    }

    @Override
    public void signalFullRoom() throws RemoteException {
        model.signalFullRoom();
    }

    @Override
    public void assignPlayerNumber(int number) throws RemoteException {
        model.assignPlayerNumber(number);
    }

    @Override
    public void serverLeft() throws RemoteException {
        model.serverLeft();
    }*/


    /**
     * Sends a pessage through the socket
     *
     * @param msg the json string
     */
    public void sendChatMessage(String msg) throws RemoteException {


    }

    /**
     * Severe connection completly
     */
/*    public void terminane() {

    }*/

    /**
     * Start uo the socket and connect to the server
     */
/*    public void connect() {

        model.clientConnected();
        System.out.println("Conectado....");

        try {
            server.registerClient(this);
        } catch (RemoteException e) {
            e.printStackTrace();
            model.showConnectionError();
        }


    }*/



    /*public void requestStartMatch(int pieceColour) throws RemoteException {

    }
    */
}
