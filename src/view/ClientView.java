package view;

/**
 * Created by ellca on 14/04/2017.
 */
public interface ClientView {
    void connectionMessage(String conectado);

    void onUserConnected();

    void receivedMessage(String mRcv);

    void showConnectionError();

    void emptyBoard();

    void setTurnPlayer(String sender);

    void setYourTurn();

    void setGameStarted(int piece);

    void addPlayerToSpace(int i, int playerNumber, int pieceColor);

    void showMyPiecesNumber(int s);

    void move(int start, int end, int playerNumber,int pieceColor);

    void warnPartnerLeft(String partnerName);

    void partnerAskForRestart(String partnerName);

    void restoreBoard();

    void performCapture(int capturedPos);

    void updateCapturedPiecesCount(int capturedPieces);

    void updateLostPiecesCount(int lostPieces);

    void anounceYouWin();

    void anounceYouLost();

    void returnNotStartedState();

    void setAllEntered();

    void serverLeft();

    void returnToUnconnectedState();

    void disablePieceSelectors();
}
