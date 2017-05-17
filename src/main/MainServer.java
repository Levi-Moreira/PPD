package main;

import network.Server;
import view.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by ellca on 28/04/2017.
 */
public class MainServer {


    private static int port = 1099;

    /**
     * MainClient for server, it lives separate from the client
     *
     * @param args
     */
    public static void main(String[] args) {

        //change the look and feel to look better
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        Server theServer = null;

        JFrame frame = new JFrame("PPD-Server");

        ServerWindow serverGui = new ServerWindow(frame);
        frame.setContentPane(serverGui.$$$getRootComponent$$$());
        frame.setSize(400, 700);
        frame.setResizable(false);
        frame.setVisible(true);

        String opt = JOptionPane.showInputDialog(frame, "Start up server?");
        if (opt != null && !"".equals(opt)) {
            try {

                serverGui.printToArea("Server is bound to the following addresses: \n");
                Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
                for (NetworkInterface netint : Collections.list(nets))
                    serverGui.displayInterfaceInformation(netint, serverGui);
                serverGui.printToArea("Connected to the port: " + port + "\n");
                serverGui.printToArea("Service name: " + opt);

                theServer = serverGui.startUpServer(opt);
                setUpServerExit(frame, theServer);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            serverGui.close();
        }

    }

    /**
     * Prepares the server for closing
     *
     * @param frame
     * @param server
     */
    private static void setUpServerExit(JFrame frame, final Server server) {

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                int i = JOptionPane.showConfirmDialog(null, "Do you really want to close this window? Clients will no longer be able to interact.");
                if (i == 0) {

                    if (server != null) {
                        try {
                            server.exit();

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    } else {
                        System.exit(0);
                    }

                }

            }
        });
    }

}
