package network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Message;
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
    private static ArrayList<PrintWriter> clients;

    private static ServerSocket serverSocket = null;

    private Socket socket = null;

    private static int port = 9090;

    private InputStream inputStream;
    private Scanner scannerIn;


    //constructor
    public Server(Socket socket) {
        this.socket = socket;

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
     * @param prwSaida the sender
     * @param msg the message
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
     * @param prwSaida the sender
     * @param msg the message
     * @throws IOException
     */
    public void sendBack(PrintWriter prwSaida, String msg) throws IOException {

        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message message = new Message(Message.TYPE_CHAT, Message.SENDER_SERVER, msg);

        String json = gson.toJson(message, type);

        prwSaida.println(json);

    }

    /**
     * Closes the server and warn the clients of this
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


    /**
     * MainClient for server, it lives separate from the client
     * @param args
     */
    public static void main(String[] args) {

        //change the look and feel to look better
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        ArrayList<Server> servers = new ArrayList<>();

        JFrame frame = new JFrame("PPD-Server");
        setUpServerExit(frame, servers);
        ServerWindow serverGui = new ServerWindow(frame);
        frame.setContentPane(serverGui.$$$getRootComponent$$$());
        frame.setSize(400, 700);
        frame.setResizable(false);
        frame.setVisible(true);

        int opt = JOptionPane.showConfirmDialog(frame, "Start up server?");
        if (opt == 0) {
            int clientsNumber = 0;
            try {

                serverSocket = new ServerSocket(port);
                clients = new ArrayList<PrintWriter>();

                serverGui.printToArea("Server is bound to the following addresses: \n");
                Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
                for (NetworkInterface netint : Collections.list(nets))
                    displayInterfaceInformation(netint, serverGui);
                serverGui.printToArea("Connected to the port: " + port + "\n");
                while (true) {

                    if (clientsNumber < 2) {
                        //System.out.println("Esperando por conecões");
                        serverGui.printToArea("Waiting clients...");
                        Socket socket = serverSocket.accept();
                        clientsNumber++;
                        //System.out.println("Cliente conectado");
                        serverGui.printToArea("Client connected!");
                        Server t = new Server(socket);
                        t.start();
                        servers.add(t);
                        serverGui.printToArea("Connected clients: " + clientsNumber);
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            serverGui.close();
        }

    }

    /**
     * Used to show the interfaces so the user can know to which he can connect
     * @param netint
     * @param serverGui
     * @throws SocketException
     */
    static void displayInterfaceInformation(NetworkInterface netint, ServerWindow serverGui) throws SocketException {

        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            if (inetAddress != null && !inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress())
                serverGui.printToArea("IP Address: " + inetAddress + "\n");
        }

    }

    /**
     * Prepares the server for closing
     * @param frame
     * @param servers
     */
    private static void setUpServerExit(JFrame frame, final ArrayList<Server> servers) {

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                int i = JOptionPane.showConfirmDialog(null, "Do you really want to close this window? Clients will no longer be able to interact.");
                if (i == 0) {

                    if (servers.size() != 0) {
                        for (Server server : servers) {
                            try {
                                server.exit();

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else {
                        System.exit(0);
                    }

                }

            }
        });
    }


}
