package model;

import network.Client;
import presenter.Presenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by ellca on 14/04/2017.
 */
public class ModelIO {

    private Client client;
    private Client serverThread;

    private Presenter presenter;

    private String clientName;

    public ModelIO(Presenter presenter) {
        this.presenter = presenter;
    }

    public void startUpClient(String clientName) {
        this.clientName = clientName;
        client = new Client(this);
    }


    public void clientConnected() {
        presenter.clientConnected();
    }


    public void sendChatMessage(String text) {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();
        Message msg = new Message(Message.TYPE_CHAT, clientName, text);

        String json = gson.toJson(msg, type);

        client.sendMessage(json);
    }

    public void receivedMessage(String mRcv) {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = gson.fromJson(mRcv, Message.class);

        if (msg != null)
            presenter.receivedMessage(msg);
    }


    public void showConnectionError() {
        presenter.showConnectionError();
    }

    public void warnStartMatch() {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();
        Message msg = new Message(Message.TYPE_GAME, Message.START_MATCH, clientName, "");

        String json = gson.toJson(msg, type);

        client.sendMessage(json);
    }

    public void endMyTurn() {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();
        Message msg = new Message(Message.TYPE_GAME, Message.END_TURN, clientName, "");

        String json = gson.toJson(msg, type);

        client.sendMessage(json);

    }

    public void addToSpace(int i, int playerNumber) {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.SUBTYPE_GAME_ADD, clientName, i + "", playerNumber + "");

        String json = gson.toJson(msg, type);

        client.sendMessage(json);
    }


    public void move(int start, int end, int playerNumber) {

        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.SUBTYPE_GAME_MOVE, clientName, playerNumber + "", start + "", end + "");

        String json = gson.toJson(msg, type);

        client.sendMessage(json);
    }

    public void terminateCLient() {

        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.CLIENT_TERMINATION, clientName,"");

        String json = gson.toJson(msg, type);

        client.sendMessage(json);
        client.terminane();
    }

    public void askForRestart() {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.ASK_RESTART_GAME, clientName,"");

        String json = gson.toJson(msg, type);

        client.sendMessage(json);
    }

    public void sendAcceptRestartMessage() {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.ACCEPT_RESTART_GAME, clientName,"");

        String json = gson.toJson(msg, type);

        client.sendMessage(json);
    }
}
