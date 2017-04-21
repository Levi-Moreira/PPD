package Presenter;

import model.Board;
import model.MainModelIO;
import model.Message;
import view.MainView;

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

        String subtype = mRcv.getSubtype();
        switch (subtype) {
            case Message.START_MATCH:
                myGui.setGameStarted();
                myGui.setTurnPlayer(mRcv.getSender());
                break;
            case Message.END_TURN:
                myGui.setYourTurn();
                break;
            case Message.TYPE_GAME_ADD:
                int space = Integer.parseInt(mRcv.getMessage());
                int player = Integer.parseInt(mRcv.getPlayer());

                board.setPlayerAtSpace(space, player);

                myGui.addPlayerToSpace(space, player);
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
        myGui.showMyPiecesumber(board.getMypieces());
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
            myGui.showMyPiecesumber(board.getMypieces());
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

    public boolean isSpaceAllowed(int space) {
        return board.isSpaceEmpty(space);
    }

    public boolean tryToMove(int[] move) {
        return board.isMoveAllowed(move[0], move[1]);
    }
}
