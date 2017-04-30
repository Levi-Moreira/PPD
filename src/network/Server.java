package network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.entities.Message;
import model.io.ServerNetworkModel;
import view.ServerWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

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
            if (!client.equals(senderClient))
                client.receiveMessage(message);
        }
    }


    private void sendBack(IClient senderClient, String message) throws RemoteException {
        String json = model.assembleServerMessage(message);
        senderClient.receiveMessage(json);
    }

    @Override
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
    public void registerClient(IClient client) throws RemoteException {

        if (clients.size() < 2) {
            clients.add(client);
            sendBack(client, clients.size() + "");
            if (clients.size() == 2) {
                sendBack(clients.get(0), Message.ALL_ENTERED);
                sendBack(clients.get(1), Message.ALL_ENTERED);
            }
        }else
        {
            sendBack(client,Message.FULL_ROOM);
        }
    }

    @Override
    public void disconnectClient(IClient client) throws RemoteException {
        clients.remove(client);
    }
}
