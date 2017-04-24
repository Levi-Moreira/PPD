package main;

import view.ClientWindow;

import javax.swing.*;

/**
 * Main entry point for the client
 */
public class Main {
    public static void main(String[] args) {

        //change the look and feel to a better experience
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        //start up the frame that will hold the main window
        JFrame frame = new JFrame("YOTÉ");

        //start up the main window
        ClientWindow myGui = new ClientWindow(frame);
        frame.setContentPane(myGui.$$$getRootComponent$$$());
        frame.setSize(1300, 1000);
        //frame.setResizable(false);

        //show it
        frame.setVisible(true);

        //start up the user interface
        myGui.startUp();


    }
}
