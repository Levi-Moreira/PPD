package view;

/**
 * Created by ellca on 14/04/2017.
 */
public interface MainView {
    void connectionMessage(String conectado);

    void onUserConnected();

    void receivedMessage(String mRcv);

    void showConnectionError();

    void emptyBoard();

    void setTurnPlayer(String sender);

    void setYourTurn();

    void setGameStarted();

    void addPlayerToSpace(int i, int playerNumber);

    void showMyPiecesNumber(int s);

    void move(int start, int end, int playerNumber);
}
