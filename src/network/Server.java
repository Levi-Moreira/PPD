package network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Message;
import view.ClientWindow;
import view.ServerWindow;

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
public class Server extends Thread {

    private boolean keepAlive = true;
    private static boolean hold = true;


    private String nome;

    private static ArrayList<PrintWriter> clients;

    private static ServerSocket serverSocket = null;
    private Socket socket = null;

    private static int port = 9090;

    InputStream in;
    Scanner scannerIn;


    public Server(Socket socket) {
        this.socket = socket;

        try {
            in = socket.getInputStream();
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

                    System.out.println(msg);

                    sendToAll(prw, msg);

                }

            }

        } catch (Exception e) {
            System.out.print("ServerRun: ");
            System.out.println(e);
        }
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

        JFrame frame = new JFrame("PPD-Server");
        ServerWindow serverGui = new ServerWindow(frame);
        frame.setContentPane(serverGui.$$$getRootComponent$$$());
        frame.setSize(400, 700);
        frame.setResizable(false);
        frame.setVisible(true);

        int opt = JOptionPane.showConfirmDialog(frame, "Iniciar Servidor?");
        if (opt == 0) {
            int clientsNumber = 0;
            try {
                serverSocket = new ServerSocket(port);
                clients = new ArrayList<PrintWriter>();


                while (true) {

                    if (clientsNumber < 2) {
                        //System.out.println("Esperando por conecões");
                        serverGui.printToArea("Esperando por conecões");
                        Socket socket = serverSocket.accept();
                        clientsNumber++;
                        //System.out.println("Cliente conectado");
                        serverGui.printToArea("Cliente conectado");
                        Server t = new Server(socket);
                        t.start();
                        serverGui.printToArea("Cliente conectados: " + clientsNumber);
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
        {
            serverGui.close();
        }

    }


}
