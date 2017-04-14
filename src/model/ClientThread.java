package model;

import Presenter.Presenter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ellca on 14/04/2017.
 */
public class ClientThread extends Thread {

    static DataOutputStream ostream = null;
    DataInputStream istream = null;
    static String host = "";
    static int port = 9090;
    Socket socket = null;
    String MRcv = "";
    static String MSnd = "";
    private MainModelIO model;


    public ClientThread( MainModelIO model) {

        this.model = model;
        try {
            socket = new Socket(host, port);
            model.clientConnected();
            System.out.println("Conectado....");
            this.start();
            ostream = new DataOutputStream(socket.getOutputStream());
            istream = new DataInputStream(socket.getInputStream());



        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        while (true) {
            try {
                MRcv = istream.readUTF();
                System.out.println("Remoto: " + MRcv);
            } catch (Exception e) {
            }
        }
    }
}
