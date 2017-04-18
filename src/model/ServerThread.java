package model;

import Presenter.Presenter;
import view.MainWindow;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ellca on 14/04/2017.
 */
public class ServerThread extends Thread {

    private boolean keepAlive = true;
    private String nome;

    private static ArrayList<BufferedWriter> clients;

    private static ServerSocket serverSocket = null;
    private Socket socket = null;

    private static int port = 9090;

    InputStream in;
    InputStreamReader inr;
    BufferedReader bfr;

    OutputStream ou;
    Writer ouw;
    BufferedWriter bfw;

    public ServerThread(Socket socket) {
        this.socket = socket;

        try {
            in = socket.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {

            String msg;
            OutputStream ou = socket.getOutputStream();
            OutputStreamWriter ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw);

            clients.add(bfw);
            nome = msg = bfr.readLine();

            while (keepAlive && msg != null) {

                msg = bfr.readLine();
                sendToAll(bfw, msg);
                System.out.println(msg);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<BufferedWriter>();

            while (clients.size()<2) {
                System.out.println("Esperando por conecões");
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado");
                Thread t = new ServerThread(socket);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
        BufferedWriter bwS;

        for (BufferedWriter bw : clients) {
            bwS = (BufferedWriter) bw;
            if (!(bwSaida == bwS)) {
                bw.write(nome + " -> " + msg + "\r\n");
                bw.flush();
            }
        }
    }


}
