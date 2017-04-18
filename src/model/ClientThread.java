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
    private String MSnd = "" + "\n";

    private boolean hasMsg = false;

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
            while (true) {
                if (hasMsg) {
                    bfw.write(MSnd);
                    bfw.flush();
                    hasMsg = false;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {
            while (keepAlive) {


                if (bfr.ready()) {

                    MRcv = "";
                    MRcv = bfr.readLine();
                    model.receivedMessage("Mensagem Recebida: " + MRcv + "\n");
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendMessage(String text) {
        hasMsg = true;
        MSnd = text + "\n";

    }
}
