package model;

import Presenter.Presenter;

import java.io.*;
import java.net.ConnectException;
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

    PrintWriter prw;

    public ClientThread(MainModelIO model) {

        this.model = model;
        try {
            socket = new Socket(host, port);
            model.clientConnected();
            System.out.println("Conectado....");
            ou = socket.getOutputStream();
            //ouw = new OutputStreamWriter(ou);
            //bfw = new BufferedWriter(ouw);

            prw = new PrintWriter(ou, true);
            prw.println("ClientName");
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

    public void run() {
        try {
            this.listen();
        } catch (Exception e) {
            System.out.print("clientlistener:");
            System.out.println(e);

        }
    }

    public void listen() throws IOException {

        InputStream in = socket.getInputStream();
        //InputStreamReader inr = new InputStreamReader(in);
        //BufferedReader bfr = new BufferedReader(inr);

        Scanner scannerIn = new Scanner(in);
        String msg = "";

        while (true)

            if (scannerIn.hasNextLine()) {
                msg = scannerIn.nextLine();
                model.receivedMessage(msg);
            }
    }

    public void sendMessage(String msg) {
        prw.println(msg);

    }
}
