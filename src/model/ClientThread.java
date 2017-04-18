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

    private boolean hasMsg = false;

    private boolean keepAlive = true;

    private MainModelIO model;

    OutputStream ou;
    Writer ouw;
    BufferedWriter bfw;


    public ClientThread(MainModelIO model) {

        this.model = model;
        try {
            socket = new Socket(host, port);
            model.clientConnected();
            System.out.println("Conectado....");
            ou = socket.getOutputStream();
            ouw = new OutputStreamWriter(ou);
            bfw = new BufferedWriter(ouw);

            bfw.write("ClientName\r\n");
            bfw.flush();
            this.start();


        } catch (
                Exception e)

        {
            System.out.println(e);
        }

    }

    public void run() {
        try {
            this.listen();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void listen() throws IOException {

        InputStream in = socket.getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);
        String msg = "";

        while (true)

            if (bfr.ready()) {
                msg = bfr.readLine();
                model.receivedMessage(msg);
            }
    }

    public void sendMessage(String msg) {

        try {
            bfw.write(msg + "\r\n");
            bfw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
