package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ellca on 14/04/2017.
 */
public class ClientThread extends Thread {
    static DataOutputStream ostream = null;
    DataInputStream istream = null;
    static String host = "";
    static int port = 9090;
    Socket socket = null;
    String MRcv = "";
    static String MSnd = "";


    public ClientThread() {
        try {
            socket = new Socket(host, port);
            System.out.println("Conectado....");
            this.start();
            ostream = new DataOutputStream(socket.getOutputStream());
            istream = new DataInputStream(socket.getInputStream());
            Scanner console = new Scanner(System.in);
            while (true) {
                System.out.println("Mensagem: ");
                String MSnd = console.nextLine();
                ostream.writeUTF(MSnd);
                ostream.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        while (true) {
            try {
                MRcv = istream.readUTF();
                System.out.println("Remoto: " + MRcv);
            } catch (Exception e) {
            }
        }
    }
}
