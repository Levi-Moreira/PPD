package model;

import Presenter.Presenter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ellca on 14/04/2017.
 */
public class Server extends Thread {

    private boolean keepAlive = true;

    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private static DataOutputStream ostream = null;
    private static int port = 9090;
    private DataInputStream istream = null;

    private String MRcv = "";
    private String MSnd = "";

    private boolean hasMsg = false;

    private MainModelIO model;

    InputStream in;
    Scanner scanner;

    PrintWriter out;
    OutputStream ou;

    private ServerReceiver receiver;

    private ServerSender sender;


    public Server(MainModelIO model) {

        this.model = model;

        this.start();
    }

    public void run() {
        try {


            serverSocket = new ServerSocket(port);
            model.waitingForConnections();
            socket = serverSocket.accept();
            model.serverConnected();

            in = socket.getInputStream();
            scanner = new Scanner(in);

            ou = socket.getOutputStream();
            out = new PrintWriter(ou, true);

            receiver = new ServerReceiver(scanner, model);
            sender = new ServerSender(out, model);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public void sendMessage(String msg) {
        sender.sendMessage(msg);
    }
}
