package model;

import Presenter.Presenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {}.getType();
        Message msg = new Message(Message.TYPE_CHAT,clientName,text);

        String json = gson.toJson(msg,type);

        clientThread.sendMessage(json);
    }

    public void receivedMessage(String mRcv) {
        presenter.receivedMessage(mRcv);
    }


    public void showConnectionError() {
        presenter.showConnectionError();
    }
}
