package model.io;

import network.Client;
import network.Server;
import presenter.ClientPresenter;
import presenter.ServerPresenter;

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

    public void startUpServer(Socket socket)
    {
        Server server = new Server(socket);
        server.start();
        mServers.add(server);

    }

}
