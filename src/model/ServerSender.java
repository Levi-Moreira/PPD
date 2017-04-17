package model;

import java.io.PrintWriter;

/**
 * Created by ellca on 17/04/2017.
 */
public class ServerSender extends Thread {

    public ServerSender(PrintWriter pw, MainModelIO model) {
        this.pw = pw;
        this.model = model;
    }

    PrintWriter pw;
    MainModelIO model;
    String MSnd;

    boolean keepAlive = true;

    boolean hasMsg = false;


    public void run() {
        try {
            while (keepAlive) {


                if (hasMsg) {
                    pw.println(MSnd);
                    hasMsg = false;
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendMessage(String msg) {
        hasMsg = true;
        MSnd = msg;
    }
}
