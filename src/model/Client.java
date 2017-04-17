package model;

import Presenter.Presenter;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ellca on 14/04/2017.
 */
public class Client {

    static String host = "";
    static int port = 9090;
    Socket socket = null;

    private String MRcv = "";
    private String MSnd = "";

    private boolean hasMsg = false;

    private boolean keepAlive = true;

    private MainModelIO model;

    private ClientReceiver receiver;

    private ClientSender sender;

    InputStream in;
    Scanner scanner;

    PrintWriter out;
    OutputStream ou;



    public Client(MainModelIO model) {

        this.model = model;

        try {
            socket = new Socket(host, port);

            model.clientConnected();

            System.out.println("Conectado....");

            in = socket.getInputStream();
            scanner = new Scanner(in);

            ou = socket.getOutputStream();
            out = new PrintWriter(ou, true);

            sender = new ClientSender(out,model);
            receiver = new ClientReceiver(scanner,model);

            sender.start();
            receiver.start();


        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void sendMessage(String text) {

        sender.sendMessage(text);

    }
}
