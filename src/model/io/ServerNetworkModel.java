package model.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.entities.Message;
import network.Client;
import network.Server;
import presenter.ClientPresenter;
import presenter.ServerPresenter;

import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by ellca on 28/04/2017.
 */
public class ServerNetworkModel {
    //the lower level client
    private Server server;

    //the upper level clientPresenter
    private ServerPresenter serverPresenter;

    private ArrayList<Server> mServers = new ArrayList<>();

    //constructor
    public ServerNetworkModel(ServerPresenter serverPresenter) {
        this.serverPresenter = serverPresenter;
    }

    public void startUpServer(Socket socket) {
        Server server = new Server(socket, this);
        server.start();
        mServers.add(server);

    }

    public String assembleServerMessage(String msg) {

        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message message = new Message(Message.TYPE_CHAT, Message.SENDER_SERVER, msg);

        return gson.toJson(message, type);
    }
}
