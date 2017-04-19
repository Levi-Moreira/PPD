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

    public ClientThread startUpClient() {
        clientThread =  new ClientThread(this);
        return  clientThread;

    }

    public ServerThread startUpServer() {
        serverThread =  new ServerThread(this);
        return serverThread;
    }

    public void clientConnected() {
        presenter.clientConnected();
    }

    public void serverConnected() {
        presenter.serverConnected();
    }

    public void sendMessage(String text) {

        if (clientThread != null)
            clientThread.sendMessage(text);

        if(serverThread!=null)
            serverThread.sendMessage(text);
    }

    public void receivedMessage(String mRcv) {
        presenter.receivedMessage(mRcv);
    }

    public void waitingForConnections() {
        presenter.waitingForConnections();
    }
}
