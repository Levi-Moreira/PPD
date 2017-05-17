package presenter;

import model.io.ServerNetworkModel;
import network.Server;
import view.ServerView;

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

    public Server startUpServer(String serviceName) {
        return modelIO.startUpServer(serviceName);
    }
}
