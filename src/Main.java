import javax.swing.*;

/**
 * Created by ellca on 12/04/2017.
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("PPD");
        frame.setContentPane(new MainWindow().$$$getRootComponent$$$());
        frame.setVisible(true);
        frame.setSize(1600,900);
        frame.setResizable(false);

    }
}
