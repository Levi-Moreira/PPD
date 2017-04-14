package model;

/**
 * Created by ellca on 14/04/2017.
 */
public class MainModelIO {

    ClientThread clientThread;
    ServerThread serverThread;

    public MainModelIO() {

    }

    public void startUpClient()
    {
        clientThread = new ClientThread();
    }

    public void startUpServer()
    {
        serverThread = new ServerThread();
    }
}
