package model;

import Presenter.Presenter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ellca on 14/04/2017.
 */
public class ServerThread extends Thread {

    private boolean keepAlive = true;

    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private static DataOutputStream ostream = null;
    private static int port = 9090;
    private DataInputStream istream = null;

    private String MRcv = "";
    private String MSnd = "" + "\n";

    private boolean hasMsg = false;

    private MainModelIO model;

    InputStream in;
    InputStreamReader inr;
    BufferedReader bfr;

    OutputStream ou;
    Writer ouw;
    BufferedWriter bfw;

    public ServerThread(MainModelIO model) {

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
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);

            ou = socket.getOutputStream();
            ouw = new OutputStreamWriter(ou);
            bfw = new BufferedWriter(ouw);

            while (keepAlive) {

                if (hasMsg) {
                    bfw.write(MSnd);
                    bfw.flush();
                    hasMsg = false;
                }

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

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public void sendMessage(String msg) {
        hasMsg = true;
        MSnd = msg + "\n";
    }
}
