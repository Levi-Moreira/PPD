package presenter;

import model.entities.Board;
import model.io.ClientNetworkModel;
import model.entities.Message;
import network.IClient;
import view.ClientView;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Class the controls the communication between the UI and the Network
 */
public class ClientPresenter {

    private ClientView UI;

    private ClientNetworkModel modelIO;

    private Board board;


    //constructor
    public ClientPresenter(ClientView UI) {
        this.UI = UI;
        try {
            modelIO = new ClientNetworkModel(this);
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
            modelIO.startUpClient(clientName, "", -1, false);
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
            modelIO.startUpClient(clientName, ip, port, true);
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
    public void sendMessage(String text) {
        modelIO.sendChatMessage(text);
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
     * Treats the received game message
     *
     * @param messageReceived the received message
     */
    public void receivedGameMessage(Message messageReceived) {
        int space;
        int player;
        int start;
        int end;
        String partnerName;

        String subtype = messageReceived.getSubtype();
        switch (subtype) {
            case Message.START_MATCH:

                break;
            case Message.END_TURN:
                UI.setYourTurn();
                break;
            case Message.SUBTYPE_GAME_ADD:
                space = Integer.parseInt(messageReceived.getMessage());
                player = Integer.parseInt(messageReceived.getPlayer());
                board.setPlayerAtSpace(space, player);
                UI.addPlayerToSpace(space, player, board.getOtherPlayerPieceColor());
                break;
            case Message.SUBTYPE_GAME_MOVE:
                start = Integer.parseInt(messageReceived.getStart_move());
                end = Integer.parseInt(messageReceived.getEnd_mode());
                player = Integer.parseInt(messageReceived.getPlayer());
                board.movePlayer(start, end, player);
                UI.move(start, end, player, board.getOtherPlayerPieceColor());
                break;
            case Message.CLIENT_TERMINATION:
                partnerName = messageReceived.getSender();
                UI.warnPartnerLeft(partnerName);
                break;
            case Message.ASK_RESTART_GAME:
                partnerName = messageReceived.getSender();
                UI.partnerAskForRestart(partnerName);
                break;
            case Message.ACCEPT_RESTART_GAME:
                restoreBoard();
                UI.restoreBoard();
                break;
            case Message.SUBTYPE_GAME_CAPTURE:
                int capturedPos = Integer.parseInt(messageReceived.getMessage());
                board.performCapture(capturedPos);
                UI.performCapture(capturedPos);
                UI.updateLostPiecesCount(board.getmLostPiecesCount());
                break;
            case Message.SUBTYPE_GAME_REMOVE:
                int removedPos = Integer.parseInt(messageReceived.getMessage());
                board.performLost(removedPos);
                UI.performCapture(removedPos);
                UI.updateLostPiecesCount(board.getmLostPiecesCount());
                break;
            case Message.ANOUNCE_WIN:
                if (board.hasLostAll() || board.isLoser())
                    UI.anounceYouLost();
                break;
            case Message.FINISH_GAME:
                int openentPieces = Integer.parseInt(messageReceived.getMessage());
                if (board.getFinishSituation(openentPieces) == Board.SITUATION_TIE) {
                    modelIO.anounceTie();
                    UI.anounceTie();
                } else {
                    modelIO.anounceNotTie();
                }
                break;
            case Message.TIE:
                UI.anounceTie();
                break;
            case Message.NOTTIE:
                int situtation = board.getAfterNotTieSituation();
                if (situtation == Board.SITUATION_WON) {
                    modelIO.anounceWin();
                    UI.anounceYouWin();
                } else {
                    if (situtation == Board.SITUATION_LOST) {
                        modelIO.anounceLost();
                        UI.anounceYouLost();
                    } else {
                        modelIO.anounceTie();
                        UI.anounceTie();
                    }
                }
                break;
            case Message.ANOUNCE_LOST:
                UI.anounceYouWin();
                break;


        }

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
    public void requestStartMatch(int pieceColour) {
        board.setmMyPiecesColour(pieceColour);
        UI.setYourTurn();
        clientConnected();
        UI.disablePieceSelectors();
        modelIO.requestStartMatch(pieceColour);
    }

    /**
     * Add this player to a space
     *
     * @param space the space to add to
     * @return true if it was successful
     */
    public boolean addToSpace(int space) {

        if (board.addSelfToSpace(space)) {
            UI.addPlayerToSpace(space, board.getmPlayerNumber(), board.getmMyPiecesColor());
            UI.showMyPiecesNumber(board.getmMyPieceCount());
            modelIO.addToSpace(space, board.getmPlayerNumber());
            return true;
        } else
            return false;
    }

    /**
     * End this player's turn
     */
    public void endMyTurn() {
        modelIO.endMyTurn();
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
            modelIO.move(move[0], move[1], board.getmPlayerNumber());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Definetly closes this client
     */
    public void terminateClient() {
        modelIO.terminateClient();
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
    public void askForResart() {
        modelIO.askForRestart();
    }

    public void refuseRestart() {
    }

    /**
     * Accepts the restart
     */
    public void acceptRestart() {
        restoreBoard();
        UI.restoreBoard();
        modelIO.sendAcceptRestartMessage();
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
        modelIO.performCapture(capturedPos);

        if (board.hasCapturedAll()) {
            modelIO.anounceWin();
            UI.anounceYouWin();
        }
    }

    /**
     * Removes a space form the board
     *
     * @param space the space where the space lives
     */
    public void removePiece(int space) {
        board.performRemoval(space);
        UI.performCapture(space);
        UI.updateCapturedPiecesCount(board.getmCapturePiecesCount());
        modelIO.performRemoval(space);

        if (board.hasCapturedAll()) {
            modelIO.anounceWin();
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
    public void finishGame() {

        modelIO.finishGame((board.getmPlayerNumber() - board.getmLostPiecesCount()));
    }


    public void startGame(int pieceColour) {
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
        modelIO.closeClient();
        UI.serverLeft();
        UI.returnToUnconnectedState();
    }

    public void signalFullRoom() {
        UI.connectionMessage("This room is already full. Try again later.");
        UI.restoreBoard();
        UI.returnToUnconnectedState();
    }

    public void assignPlayerNumber(int playerNUmber) {
        startUpBoard(playerNUmber);
    }
}
