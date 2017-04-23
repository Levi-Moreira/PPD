import view.ClientWindow;

import javax.swing.*;

/**
 * Created by ellca on 12/04/2017.
 */
public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("PPD");
        ClientWindow myGui = new ClientWindow(frame);
        frame.setContentPane(myGui.$$$getRootComponent$$$());
        frame.setSize(1300, 1000);
        frame.setResizable(false);
        frame.setVisible(true);
        myGui.startUp();


    }
}
