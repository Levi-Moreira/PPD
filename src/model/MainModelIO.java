package model;

import Presenter.Presenter;

/**
 * Created by ellca on 14/04/2017.
 */
public class MainModelIO {

    private ClientThread clientThread;
    private ServerThread serverThread;

    private Presenter presenter;

    public MainModelIO(Presenter presenter) {
        this.presenter = presenter;
    }

    public void startUpClient() {
        clientThread = new ClientThread(this);
    }

    public void startUpServer() {
        serverThread = new ServerThread(this);
    }

    public void clientConnected() {
        presenter.clientConnected();
    }

    public void serverConnected() {
        presenter.serverConnected();
    }
}
