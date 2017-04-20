import Presenter.Presenter;
import view.MainWindow;

import javax.swing.*;

/**
 * Created by ellca on 12/04/2017.
 */
public class Main {
    public static void main(String[] args) {

        MainWindow myGui = new MainWindow();
        JFrame frame = new JFrame("PPD");
        frame.setContentPane(myGui.$$$getRootComponent$$$());
        frame.setSize(1000, 900);
        frame.setResizable(false);
        frame.setVisible(true);
        myGui.startUp();


    }
}
