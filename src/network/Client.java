package network;

import model.ModelIO;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ellca on 14/04/2017.
 */
public class Client extends Thread {

    static String host = "";
    static int port = 9090;
    Socket socket = null;


    private boolean keepAlive = true;

    private ModelIO model;

    private OutputStream ou;

    private PrintWriter prw;

    private boolean remote;

    public Client(ModelIO model) {

        this.model = model;
        remote = false;


    }

    public Client(ModelIO modelIO, String ip, int port) {
        remote = true;
        this.model = modelIO;
        host = ip;
        this.port = port;

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

        while (keepAlive)

            if (scannerIn.hasNextLine()) {
                msg = scannerIn.nextLine();
                model.receivedMessage(msg);
            }
    }

    public void sendMessage(String msg) {
        prw.println(msg);

    }

    public void terminane() {
        keepAlive = false;
        try {
            socket.close();
            prw.close();
            ou.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            model.clientConnected();
            System.out.println("Conectado....");
            ou = socket.getOutputStream();


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

    public void close() {
        keepAlive = false;
        try {
            socket.close();
            prw.close();
            ou.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
