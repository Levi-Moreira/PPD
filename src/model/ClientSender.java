package model;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by ellca on 17/04/2017.
 */
public class ClientSender extends Thread {

    public ClientSender(PrintWriter pw, MainModelIO model) {
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

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isHasMsg() {
        return hasMsg;
    }

    public void setHasMsg(boolean hasMsg) {
        this.hasMsg = hasMsg;
    }

    public void sendMessage(String text) {
        hasMsg = true;
        MSnd = text;
    }
}
