package model;

import Presenter.Presenter;

/**
 * Created by ellca on 14/04/2017.
 */
public class MainModelIO {

    private ClientThread clientThread;
    private ClientThread serverThread;

    private Presenter presenter;

    private String clientName;

    public MainModelIO(Presenter presenter) {
        this.presenter = presenter;
    }

    public void startUpClient(String clientName) {
        this.clientName = clientName;
        clientThread = new ClientThread(this);
    }

    public void startUpServer() {
        serverThread = new ClientThread(this);
    }

    public void clientConnected() {
        presenter.clientConnected();
    }


    public void sendMessage(String text) {
        clientThread.sendMessage(text);
    }

    public void receivedMessage(String mRcv) {
        presenter.receivedMessage(mRcv);
    }


    public void showConnectionError() {
        presenter.showConnectionError();
    }
}
