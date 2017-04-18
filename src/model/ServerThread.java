package model;

import Presenter.Presenter;
import view.MainWindow;

import javax.swing.*;
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

    InputStream in;
    InputStreamReader inr;
    BufferedReader bfr;

    OutputStream ou;
    Writer ouw;
    BufferedWriter bfw;

    public ServerThread() {


        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Esperando por conecões");
            socket = serverSocket.accept();
            System.out.println("Cliente conectado");
            in = socket.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);

            ou = socket.getOutputStream();
            ouw = new OutputStreamWriter(ou);
            bfw = new BufferedWriter(ouw);
        } catch (Exception e) {
            System.out.println(e);
        }

        this.start();

        while (true) {
            try {
                if (hasMsg) {
                    bfw.write(MSnd);
                    bfw.flush();
                    hasMsg = false;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void run() {
        try {

            while (keepAlive) {

                if (bfr.ready()) {
                    MRcv = "";
                    MRcv = bfr.readLine();
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public static void main(String[] args) {
        ServerThread serverThread = new ServerThread();

    }


}
