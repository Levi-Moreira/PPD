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
        model = new MainModelIO(this);
    }

    public void startUpClient(String clientName) {
        model.startUpClient(clientName);
    }

    public void startUpServer() {
        model.startUpServer();
    }

    public void clientConnected() {
        myGui.connectionMessage("Conectado");
        myGui.onUserConnected();
    }

    public void serverConnected() {
        myGui.connectionMessage("Conectado");
        myGui.onUserConnected();
    }

    public void sendMessage(String text) {
        model.sendMessage(text);
    }

    public void receivedMessage(String mRcv) {
        myGui.receivedMessage(mRcv);
    }

    public void waitingForConnections() {
        myGui.connectionMessage("Esperando por conexões");
    }

    public void showConnectionError() {
        myGui.showConnectionError();
    }
}
