package Presenter;

import model.Client;
import model.MainModelIO;
import model.Server;
import view.MainView;
import view.MainWindow;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ellca on 14/04/2017.
 */
public class Presenter {

    private MainView myGui;

    private MainModelIO model;

    private ArrayList<Client> mClients = new ArrayList<>();

    private Server mServer;


    public Presenter(MainView myGui) {
        this.myGui = myGui;
        model = new MainModelIO(this);
    }

    public void startUpClient() {
        mClients.add(model.startUpClient());
    }

    public void startUpServer() {

        mServer = model.startUpServer();
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
}
