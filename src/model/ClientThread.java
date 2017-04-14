package model;

import Presenter.Presenter;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ellca on 14/04/2017.
 */
public class ClientThread extends Thread {

    static String host = "";
    static int port = 9090;
    Socket socket = null;

    private String MRcv = "";
    private String MSnd = "";

    private boolean sendReceive = true;
    private boolean hasMsg = true;

    private boolean keepAlive = true;

    private MainModelIO model;

    InputStream in;
    InputStreamReader inr;
    BufferedReader bfr;

    OutputStream ou;
    Writer ouw;
    BufferedWriter bfw;


    public ClientThread(MainModelIO model) {

        this.model = model;
        try {
            socket = new Socket(host, port);
            model.clientConnected();
            System.out.println("Conectado....");
            this.start();
            in = socket.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);

            ou = socket.getOutputStream();
            ouw = new OutputStreamWriter(ou);
            bfw = new BufferedWriter(ouw);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {
            while (keepAlive) {

                if (hasMsg) {
                    bfw.write(MSnd);
                    bfw.flush();
                    hasMsg = false;

                }

                if (bfr.ready()) {
                    MRcv = bfr.readLine();
                    model.receivedMessage("Mensagem Recebida: " + MRcv+"\r\n");
                    System.out.println("Remoto: " + MRcv);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendMessage(String text) {
        hasMsg = true;
        MSnd = text+"\r\n";

    }
}
