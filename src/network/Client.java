package network;

import model.io.ClientNetworkModel;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Client class that generates the main communication hub
 */
public class Client extends UnicastRemoteObject implements IClient, Runnable {


    private final IServer server;
    private ClientNetworkModel model;


    //constructor for local connections
    public Client(IServer server, ClientNetworkModel model) throws RemoteException {
        super();

        this.model = model;
        this.server = server;


    }

    @Override
    public void receiveMessage(String msg) {
        model.receivedMessage(msg);
    }

    /**
     * Thread run
     */
    public void run() {

    }


    /**
     * Sends a pessage through the socket
     *
     * @param msg the json string
     */
    public void sendMessage(String msg) throws RemoteException {
        server.receiveMessage(this, msg);

    }

    /**
     * Severe connection completly
     */
    public void terminane() {

    }

    /**
     * Start uo the socket and connect to the server
     */
    public void connect() {

        model.clientConnected();
        System.out.println("Conectado....");

        try {
            server.registerClient(this);
        } catch (RemoteException e) {
            e.printStackTrace();
            model.showConnectionError();
        }


    }

    /**
     * Close this client, but leave thread running so it can be restarted
     */
    public void close() {
        try {
            server.disconnectClient(this);
        } catch (RemoteException e) {
            e.printStackTrace();
            model.showConnectionError();
        }
    }
}
