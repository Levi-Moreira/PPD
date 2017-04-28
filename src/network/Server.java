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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * The server class for the yote game
 */
public class Server extends Thread {

    private boolean keepAlive = true;

    //holds the clients
    private static ArrayList<PrintWriter> clients = new ArrayList<>();

    private Socket socket = null;

    private InputStream inputStream;
    private Scanner scannerIn;

    private ServerNetworkModel model;


    //constructor
    public Server(Socket socket, ServerNetworkModel model) {
        this.socket = socket;
        this.model = model;

        try {
            inputStream = socket.getInputStream();
            scannerIn = new Scanner(inputStream);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Thread run
     */
    public void run() {
        try {

            //start up the connection for writing
            String msg;
            OutputStream ou = socket.getOutputStream();
            PrintWriter prw = new PrintWriter(ou, true);

            //add client to acess list
            clients.add(prw);
            msg = scannerIn.nextLine();

            //send to the recenlty connected client its playerNumber
            sendBack(prw, clients.size() + "");

            //anounce that all clients are connected
            if (clients.size() == 2) {
                sendBack(clients.get(0), Message.ALL_ENTERED);
                sendBack(clients.get(1), Message.ALL_ENTERED);
            }

            //receives messages and send them to the other client
            while (keepAlive && msg != null) {
                if (scannerIn.hasNextLine()) {
                    msg = scannerIn.nextLine();
                    System.out.println(msg);

                    //if a client tries to start a match without all users connected, warn him
                    if (msg.contains(Message.START_MATCH) && clients.size() < 2) {
                        sendBack(prw, Message.NOT_ENOUGH_CLIENTS);
                    } else {
                        //normally send message to other client
                        sendToAll(prw, msg);
                    }

                }

            }

        } catch (Exception e) {
            System.out.print("ServerRun: ");
            System.out.println(e);
        }
    }


    /**
     * Send a message to all clients but the sender
     *
     * @param prwSaida the sender
     * @param msg      the message
     * @throws IOException
     */
    public void sendToAll(PrintWriter prwSaida, String msg) throws IOException {
        PrintWriter bwS;

        for (PrintWriter pw : clients) {
            bwS = (PrintWriter) pw;

            if (!(prwSaida == bwS)) {
                pw.println(msg);
            }

        }
    }

    /**
     * Sends a message back to the sender
     *
     * @param prwSaida the sender
     * @param msg      the message
     * @throws IOException
     */
    public void sendBack(PrintWriter prwSaida, String msg) throws IOException {

        String json = model.assembleServerMessage(msg);
        prwSaida.println(json);

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

        keepAlive = false;
        System.exit(0);
    }

}
