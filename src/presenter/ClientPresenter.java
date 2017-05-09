package presenter;

import model.entities.Board;
import network.Client;
import network.IClient;
import view.ClientView;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Class the controls the communication between the UI and the Network
 */
public class ClientPresenter {

    private ClientView UI;

    private Client client;

    private Board board;


    //constructor
    public ClientPresenter(ClientView UI) {
        this.UI = UI;
        try {
            client = new Client(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start up a client for a local connection
     *
     * @param clientName the client name
     */
    public void startUpClient(String clientName) {
        try {
            client.startUpClient(clientName, "", -1, false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            showConnectionError();
        }
    }

    /**
     * Start up a client for a remote connection
     *
     * @param clientName the client name
     * @param ip         the ip address of the server
     * @param port       the port number of the server
     */
    public void startUpClient(String clientName, String ip, int port) {

        try {
            client.startUpClient(clientName, ip, port, true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            showConnectionError();
        }
    }

    /**
     * Starts up the board abstraction
     *
     * @param playerNUmber the number of this player
     */
    private void startUpBoard(int playerNUmber) {
        board = new Board(playerNUmber);
        UI.emptyBoard();
        UI.showMyPiecesNumber(board.getmMyPieceCount());
    }

    /**
     * Prints to the UI the message that the client is connecte
     */
    public void clientConnected() {
        UI.connectionMessage("Connected");
        UI.onUserConnected();
    }


    /**
     * Sends a Chat message
     *
     * @param text the message to be sent
     */
    public void sendChatMessage(String text) {
        client.sendChatMessage(text);
    }

    /**
     * Treats the received chat Message
     *
     * @param messageReceived the object message
     */
    public void receivedChatMessage(IClient sender, String messageReceived) throws RemoteException {
        UI.receivedMessage(sender.getName() + " says -> " + messageReceived);
    }

    /**
     * Print connection error to UI
     */
    public void showConnectionError() {
        UI.showConnectionError();
    }

    /**
     * Warns the begining of a game to the other player
     *
     * @param pieceColour the colour of the player's piece
     */
    public void performStartMatch(int pieceColour) {
        board.setmMyPiecesColour(pieceColour);
        UI.setYourTurn();
        clientConnected();
        UI.disablePieceSelectors();
        client.performStartMatch(pieceColour);
    }

    /**
     * Add this player to a space
     *
     * @param space the space to add to
     * @return true if it was successful
     */
    public boolean performAddToSpace(int space) {

        if (board.addSelfToSpace(space)) {
            UI.addPlayerToSpace(space, board.getmPlayerNumber(), board.getmMyPiecesColor());
            UI.showMyPiecesNumber(board.getmMyPieceCount());
            client.performAddToSpace(space, board.getmPlayerNumber());
            return true;
        } else
            return false;
    }

    /**
     * End this player's turn
     */
    public void performEndMyTurn() {
        client.performEndMyTurn();
        UI.setTurnPlayer("Other Player");
    }

    /**
     * Checks if this player still has pieces
     *
     * @return true if so
     */
    public boolean hasPieces() {
        return board.stillHavePieces();
    }

    /**
     * Checks if a space belngs to this player
     *
     * @param space the space to be cosnidered
     * @return true if so
     */
    public boolean isSpaceMine(int space) {
        return board.isSpaceMine(space);
    }

    /**
     * Checks if a space is empty
     *
     * @param space the space to be considred
     * @return true if so
     */
    public boolean isSpaceEmpty(int space) {
        return board.isSpaceEmpty(space);
    }

    /**
     * Try to perform a move
     *
     * @param move the move in a 2D array
     * @return true if possible
     */
    public boolean tryToMove(int[] move) {
        if (board.isMoveAllowed(move[0], move[1])) {
            board.movePlayer(move[0], move[1], board.getmPlayerNumber());
            UI.move(move[0], move[1], board.getmPlayerNumber(), board.getmMyPiecesColor());
            client.move(move[0], move[1], board.getmPlayerNumber());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Definetly closes this client
     */
    public void terminateClient() {
        client.terminateClient();
    }

    /**
     * Restarts the board
     */
    public void restoreBoard() {
        board.restoreBoard();
        UI.showMyPiecesNumber(board.getmMyPieceCount());
        UI.updateCapturedPiecesCount(board.getmCapturePiecesCount());
        UI.updateLostPiecesCount(board.getmLostPiecesCount());
    }

    /**
     * Ask for a rest to the other user
     */
    public void performResart() {
        client.performResart();
    }

    public void refuseRestart() {
    }

    /**
     * Accepts the restart
     */
    public void performAcceptRestart() {
        restoreBoard();
        UI.restoreBoard();
        client.performAcceptRestart();
    }

    /**
     * Performs a capture
     *
     * @param move the move representing the capture
     */
    public void performCapture(int[] move) {
        int capturedPos = board.performCapture(move[0], move[1]);
        UI.performCapture(capturedPos);
        UI.updateCapturedPiecesCount(board.getmCapturePiecesCount());
        client.performCapture(capturedPos);

        if (board.hasCapturedAll()) {
            client.anounceWin();
            UI.anounceYouWin();
        }
    }

    /**
     * Removes a space form the board
     *
     * @param space the space where the space lives
     */
    public void performRemovePiece(int space) {
        board.performRemoval(space);
        UI.performCapture(space);
        UI.updateCapturedPiecesCount(board.getmCapturePiecesCount());
        client.performRemovePiece(space);

        if (board.hasCapturedAll()) {
            client.anounceWin();
            UI.anounceYouWin();
        }
    }

    /**
     * Checks if a capture is possible in a move
     *
     * @param move the move
     * @return true if so
     */
    public boolean hasCapture(int[] move) {
        return board.isCapturePossible(move[0], move[1]);
    }

    /**
     * Checks if the user can still capture
     *
     * @return
     */
    public boolean canStillCapture() {

        return board.isCaptureGenerallyPossible();
    }

    /**
     * Checks if the oponent has pieces on the board
     *
     * @return
     */
    public boolean oponentHasPiecesOnBoard() {
        return board.oponentHasPiecesOnBoard();
    }

    /**
     * Checks if the capture is possible form a certain space
     *
     * @param space the space to start the move
     * @return
     */
    public boolean checkCapturePossibility(int space) {
        return board.checkCapturePossibility(space);
    }

    /**
     * Ask for the game to be finished
     */
    public void performFinishGame() {

        client.performFinishGame((board.getmPlayerNumber() - board.getmLostPiecesCount()));
    }


    public void actOnStartGame(int pieceColour) {
        int piece = pieceColour;
        int myPiece = (piece == Board.PIECE_COLOR_RED) ? Board.PIECE_COLOR_BLACK : Board.PIECE_COLOR_RED;
        UI.setGameStarted(myPiece);
        UI.setTurnPlayer("Other Player");
        board.setmMyPiecesColour(myPiece);
    }

    public void signalNotEnoughClients() {
        UI.connectionMessage("Wait for the other player.");
        UI.returnNotStartedState();
    }

    public void signalAllEntered() {
        UI.setAllEntered();
    }

    public void signalServerLeft() {
        UI.serverLeft();
        UI.returnToUnconnectedState();
        client.closeClient();
    }

    public void signalFullRoom() {
        UI.connectionMessage("This room is already full. Try again later.");
        UI.restoreBoard();
        UI.returnToUnconnectedState();
    }

    public void assignPlayerNumber(int playerNUmber) {
        startUpBoard(playerNUmber);
    }

    public void actOnStartTurn() {
        UI.setYourTurn();
    }

    public void actOnAddPlayerToSpace(int space, int playerNumber) {
        board.setPlayerAtSpace(space, playerNumber);
        UI.addPlayerToSpace(space, playerNumber, board.getOtherPlayerPieceColor());
    }

    public void actOnPerformMove(int start, int end, int playerNumber) {
        board.movePlayer(start, end, playerNumber);
        UI.move(start, end, playerNumber, board.getOtherPlayerPieceColor());

    }

    public void actOnCapture(int capturedPos) {
        board.performCapture(capturedPos);
        UI.performCapture(capturedPos);
        UI.updateLostPiecesCount(board.getmLostPiecesCount());
    }

    public void anouncePartnerLeft(String clientName) {
        UI.warnPartnerLeft(clientName);
    }

    public void actOnRestartRequest(String partnerName) {
        UI.partnerAskForRestart(partnerName);
    }

    public void actOnAcceptedRestart() {
        restoreBoard();
        UI.restoreBoard();
    }

    public void otherClientWon() {
        if (board.hasLostAll() || board.isLoser())
            UI.anounceYouLost();
    }

    public void actOnRemoveSpace(int removePos) {
        board.performLost(removePos);
        UI.performCapture(removePos);
        UI.updateLostPiecesCount(board.getmLostPiecesCount());
    }

    public void actOnFinishGame(int piecesOnBoard) {
        if (board.getFinishSituation(piecesOnBoard) == Board.SITUATION_TIE) {
            client.anounceTie();
            UI.anounceTie();
        } else {
            client.anounceNotTie();
        }
    }

    public void actOnAnnounceTie() {
        UI.anounceTie();
    }

    public void actOnAnnounceNoTie() {
        int situtation = board.getAfterNotTieSituation();
        if (situtation == Board.SITUATION_WON) {
            client.anounceWin();
            UI.anounceYouWin();
        } else {
            if (situtation == Board.SITUATION_LOST) {
                client.anounceLost();
                UI.anounceYouLost();
            } else {
                client.anounceTie();
                UI.anounceTie();
            }
        }
    }

    public void otherClientLost() {
        UI.anounceYouWin();
    }
}
