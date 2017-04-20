package model;

import Presenter.Presenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import view.MainWindow;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
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

    private static ArrayList<PrintWriter> clients;

    private static ServerSocket serverSocket = null;
    private Socket socket = null;

    private static int port = 9090;

    InputStream in;
    Scanner scannerIn;


    public ServerThread(Socket socket) {
        this.socket = socket;

        try {
            in = socket.getInputStream();
            //inr = new InputStreamReader(in);
            //bfr = new BufferedReader(inr);
            scannerIn = new Scanner(in);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {

            String msg;
            OutputStream ou = socket.getOutputStream();
            PrintWriter prw = new PrintWriter(ou, true);

            clients.add(prw);
            nome = msg = scannerIn.nextLine();
            sendBack(prw, clients.size() + "");

            while (keepAlive && msg != null) {
                if (scannerIn.hasNextLine()) {
                    msg = scannerIn.nextLine();
                    sendToAll(prw, msg);
                    System.out.println(msg);
                }

            }
        } catch (Exception e) {
            System.out.print("ServerRun: ");
            System.out.println(e);
        }
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }


    public void sendToAll(PrintWriter prwSaida, String msg) throws IOException {
        PrintWriter bwS;

        for (PrintWriter pw : clients) {
            bwS = (PrintWriter) pw;
            if (!(prwSaida == bwS)) {
                pw.println(msg);
            }
        }
    }

    public void sendBack(PrintWriter prwSaida, String msg) throws IOException {

        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message message = new Message(Message.TYPE_CHAT, Message.SENDER_SERVER, msg);

        String json = gson.toJson(message, type);

        prwSaida.println(json);

    }

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<PrintWriter>();

            while (clients.size() < 2) {
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


}
