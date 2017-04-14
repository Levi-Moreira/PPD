package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    private static String MSnd = "";

    private boolean sendReceive = true;
    private boolean hasMsg = true;

    public ServerThread() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Aguardando conexão...");
            socket = serverSocket.accept();
            System.out.println("Conexão Estabelecida.");
            ostream = new DataOutputStream(socket.getOutputStream());
            istream = new DataInputStream(socket.getInputStream());

            this.start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run(){
        try {
            while(keepAlive){
                if(hasMsg && sendReceive) {
                    ostream.writeUTF(MSnd);
                    ostream.flush();
                    MRcv = istream.readUTF();
                    hasMsg = false;
                }


            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public void sendMessage(String msg)
    {
        hasMsg = true;
        sendReceive = true;
        MSnd = msg;
    }
}
