package Presenter;

import model.MainModelIO;
import view.MainView;
import view.MainWindow;

/**
 * Created by ellca on 14/04/2017.
 */
public class Presenter {

    private MainView myGui;

    private MainModelIO model;


    public Presenter(MainView myGui) {
        this.myGui = myGui;
        model = new MainModelIO();
    }

    public void startUpClient() {
        model.startUpClient();
    }

    public void startUpServer() {
        model.startUpServer();
    }
}
