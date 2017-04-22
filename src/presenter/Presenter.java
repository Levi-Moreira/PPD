package presenter;

import model.Board;
import model.ModelIO;
import model.Message;
import view.MainView;

/**
 * Created by ellca on 14/04/2017.
 */
public class Presenter {

    private MainView myGui;

    private ModelIO model;

    private Board board;


    public Presenter(MainView myGui) {
        this.myGui = myGui;
        model = new ModelIO(this);
    }

    public void startUpClient(String clientName) {
        model.startUpClient(clientName);
    }


    public void clientConnected() {
        myGui.connectionMessage("Conectado");
        myGui.onUserConnected();
    }


    public void sendMessage(String text) {
        model.sendChatMessage(text);
    }

    public void receivedChatMessage(Message mRcv) {
        if (mRcv.isFromServer()) {
            int playerNUmber = Integer.parseInt(mRcv.getMessage());
            startUpBoard(playerNUmber);
        } else {
            myGui.receivedMessage(mRcv.getSender() + " diz-> " + mRcv.getMessage());
        }
    }

    public void receivedGameMessage(Message mRcv) {
        int space;
        int player;
        int start;
        int end;
        String partnerName;

        String subtype = mRcv.getSubtype();
        switch (subtype) {
            case Message.START_MATCH:
                myGui.setGameStarted();
                myGui.setTurnPlayer(mRcv.getSender());
                break;
            case Message.END_TURN:
                myGui.setYourTurn();
                break;
            case Message.SUBTYPE_GAME_ADD:
                space = Integer.parseInt(mRcv.getMessage());
                player = Integer.parseInt(mRcv.getPlayer());
                board.setPlayerAtSpace(space, player);
                myGui.addPlayerToSpace(space, player);
                break;
            case Message.SUBTYPE_GAME_MOVE:
                start = Integer.parseInt(mRcv.getStart_move());
                end = Integer.parseInt(mRcv.getEnd_mode());
                player = Integer.parseInt(mRcv.getPlayer());
                board.movePlayer(start, end, player);
                myGui.move(start, end, player);
                break;
            case Message.CLIENT_TERMINATION:
                partnerName = mRcv.getSender();
                myGui.warnPartnerLeft(partnerName);
                break;
            case Message.ASK_RESTART_GAME:
                partnerName = mRcv.getSender();
                myGui.partnerAskForRestart(partnerName);
                break;
            case Message.ACCEPT_RESTART_GAME:
                restoreBoard();
                myGui.restoreBoard();
                break;
            case Message.SUBTYPE_GAME_CAPTURE:
                int capturedPos = Integer.parseInt(mRcv.getMessage());
                board.performCapture(capturedPos);
                myGui.performCapture(capturedPos);
                myGui.updateLostPiecesCount(board.getLostPieces());
                break;
            case Message.SUBTYPE_GAME_REMOVE:
                int removedPos = Integer.parseInt(mRcv.getMessage());
                board.performLost(removedPos);
                myGui.performCapture(removedPos);
                myGui.updateLostPiecesCount(board.getLostPieces());
                break;


        }

    }

    public void receivedMessage(Message mRcv) {

        if (mRcv.isChat()) {
            receivedChatMessage(mRcv);

        } else {
            receivedGameMessage(mRcv);


        }
    }

    private void startUpBoard(int playerNUmber) {
        board = new Board(playerNUmber);
        myGui.emptyBoard();
        myGui.showMyPiecesNumber(board.getMypieces());
    }

    public void showConnectionError() {
        myGui.showConnectionError();
    }

    public void warnStartMach() {
        model.warnStartMatch();
        myGui.setYourTurn();
    }

    public boolean addToSpace(int i) {

        if (board.addSelfToSpace(i)) {
            myGui.addPlayerToSpace(i, board.getPlayerNumber());
            myGui.showMyPiecesNumber(board.getMypieces());
            model.addToSpace(i, board.getPlayerNumber());
            return true;
        } else
            return false;
    }

    public void endMyTurn() {
        model.endMyTurn();
        myGui.setTurnPlayer("Outro");
    }

    public boolean hasPieces() {
        return board.stillHavePieces();
    }

    public boolean isSpaceMine(int space) {
        return board.isSpaceMine(space);
    }

    public boolean isSpaceEmpty(int space) {
        return board.isSpaceEmpty(space);
    }

    public boolean tryToMove(int[] move) {
        if (board.isMoveAllowed(move[0], move[1])) {
            board.movePlayer(move[0], move[1], board.getPlayerNumber());
            myGui.move(move[0], move[1], board.getPlayerNumber());
            model.move(move[0], move[1], board.getPlayerNumber());
            return true;
        } else {
            return false;
        }
    }

    public void terminateClient() {
        model.terminateCLient();
    }

    public void restoreBoard() {
        board.restoreBoard();
        myGui.showMyPiecesNumber(board.getMypieces());
    }

    public void askForResart() {
        model.askForRestart();
    }

    public void refuseRestart() {
    }

    public void acceptRestart() {
        restoreBoard();
        myGui.restoreBoard();
        model.sendAcceptRestartMessage();
    }

    public void performCapture(int[] move) {
        int capturedPos = board.performCapture(move[0], move[1]);
        myGui.performCapture(capturedPos);
        myGui.updateCapturedPiecesCount(board.getCapturedPieces());
        model.performCapture(capturedPos);
    }

    public void removePiece(int piece) {
        board.performRemoval(piece);
        myGui.performCapture(piece);
        myGui.updateCapturedPiecesCount(board.getCapturedPieces());
        model.performRemoval(piece);
    }

    public boolean hasCapture(int[] move) {
        return board.isCapturePossible(move[0], move[1]);
    }

    public boolean canStillCapture(int start) {
        return board.isCapturePossible(start);
    }
}
