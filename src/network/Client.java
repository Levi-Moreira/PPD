package network;

import model.ClientNetworkModel;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client class that generates the main communication hub
 */
public class Client extends Thread {

    private static String host = "";
    private static int port = 9090;
    private Socket socket = null;


    private boolean keepAlive = true;

    private ClientNetworkModel model;

    private OutputStream outputStream;

    private PrintWriter printWriter;

    private boolean remote;

    //constructor for local connections
    public Client(ClientNetworkModel model) {

        this.model = model;
        remote = false;


    }

    //constructor for remote connection
    public Client(ClientNetworkModel clientNetworkModel, String ip, int port) {
        remote = true;
        this.model = clientNetworkModel;
        host = ip;
        this.port = port;

    }

    /**
     * Thread run
     */
    public void run() {
        try {
            this.listen();
        } catch (Exception e) {
            System.out.print("clientlistener:");
            System.out.println(e);

        }
    }

    /**
     * Waits for an incoming message
     * @throws IOException
     */
    public void listen() throws IOException {

        InputStream in = socket.getInputStream();

        Scanner scannerIn = new Scanner(in);
        String msg = "";

        while (keepAlive)
            //pass the json string to the model for decodification
            if (scannerIn.hasNextLine()) {
                msg = scannerIn.nextLine();
                model.receivedMessage(msg);
            }
    }

    /**
     * Sends a pessage through the socket
     * @param msg the json string
     */
    public void sendMessage(String msg) {
        printWriter.println(msg);

    }

    /**
     * Severe connection completly
     */
    public void terminane() {
        keepAlive = false;
        try {
            socket.close();
            printWriter.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start uo the socket and connect to the server
     */
    public void connect() {
        try {
            socket = new Socket(host, port);
            model.clientConnected();
            System.out.println("Conectado....");
            outputStream = socket.getOutputStream();


            printWriter = new PrintWriter(outputStream, true);
            printWriter.println("ClientName");
            this.start();


        } catch (Exception e) {
            System.out.print("clientconstructor:");
            System.out.println(e);
            e.printStackTrace();
            if (e instanceof ConnectException) {
                model.showConnectionError();
            }
        }
    }

    /**
     * Close this client, but leave thread running so it can be restarted
     */
    public void close() {
        keepAlive = false;
        try {
            socket.close();
            printWriter.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
