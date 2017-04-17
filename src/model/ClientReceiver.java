package model;

import sun.applet.Main;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by ellca on 17/04/2017.
 */
public class ClientReceiver extends Thread {


    public ClientReceiver(Scanner scanner, MainModelIO model) {
        this.scanner = scanner;
        this.model = model;
    }

    Scanner scanner;
    MainModelIO model;
    String MRcv;

    boolean keepAlive = true;

    public void run() {
        try {
            while (keepAlive) {

                if (scanner.hasNextLine()) {
                    MRcv = "";
                    MRcv = scanner.nextLine();
                    model.receivedMessage("Mensagem Recebida: " + MRcv + "\n");
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
