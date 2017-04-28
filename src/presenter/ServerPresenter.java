package presenter;

import model.entities.Board;
import model.io.ClientNetworkModel;
import model.io.ServerNetworkModel;
import view.ClientView;
import view.ServerView;

import java.net.Socket;

/**
 * Created by ellca on 28/04/2017.
 */
public class ServerPresenter {

    private ServerView UI;

    private ServerNetworkModel modelIO;


    //constructor
    public ServerPresenter(ServerView UI) {
        this.UI = UI;
        modelIO = new ServerNetworkModel(this);
    }

    public void startUpServer(Socket socket) {
        modelIO.startUpServer(socket);
    }
}
