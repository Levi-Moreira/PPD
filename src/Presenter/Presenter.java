package Presenter;

import model.Board;
import model.MainModelIO;
import model.Message;
import view.MainView;
import view.MainWindow;

/**
 * Created by ellca on 14/04/2017.
 */
public class Presenter {

    private MainView myGui;

    private MainModelIO model;

    private Board board;


    public Presenter(MainView myGui) {
        this.myGui = myGui;
        model = new MainModelIO(this);
    }

    public void startUpClient(String clientName) {
        model.startUpClient(clientName);
    }

    public void startUpServer() {
        model.startUpServer();
    }

    public void clientConnected() {
        myGui.connectionMessage("Conectado");
        myGui.onUserConnected();
    }

    public void serverConnected() {
        myGui.connectionMessage("Conectado");
        myGui.onUserConnected();
    }

    public void sendMessage(String text) {
        model.sendMessage(text);
    }

    public void receivedMessage(Message mRcv) {

        if (mRcv.isChat()) {

            if (mRcv.isFromServer()) {
                int playerNUmber = Integer.parseInt(mRcv.getMessage());
                startUpBoard(playerNUmber);
            } else {
                myGui.receivedMessage(mRcv.getSender() + " diz-> " + mRcv.getMessage());
            }
        }else
        {
            if(mRcv.isStartMatch())
            {
                //myGui.enableBoard(false);
                myGui.setGameStarted();
                myGui.setTurnPlayer(mRcv.getSender());
            }
        }
    }

    private void startUpBoard(int playerNUmber) {
        board = new Board(playerNUmber);
        myGui.emptyBoard();
    }

    public void waitingForConnections() {
        myGui.connectionMessage("Esperando por conexões");
    }

    public void showConnectionError() {
        myGui.showConnectionError();
    }

    public void warnStartMach() {
        model.warnStartMatch();
        myGui.setYourTurn();
    }
}
