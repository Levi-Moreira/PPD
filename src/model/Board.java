package model;

/**
 * Created by ellca on 20/04/2017.
 */
public class Board {

    public static final int TOTAL_PIECES = 5;
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    private int playerNumber;

    private int[] board;

    private int mypieces;

    private int capturedPieces;

    private int playedPieces;

    public Board(int playerNumber) {
        board = new int[30];

        for (int i = 0; i < 30; i++)
            board[i] = -1;


        this.playerNumber = playerNumber;
        mypieces = TOTAL_PIECES;
        capturedPieces = 0;
        playedPieces = 0;

    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }


    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public int getMypieces() {
        return mypieces;
    }

    public void setMypieces(int mypieces) {
        this.mypieces = mypieces;
    }

    public int getCapturedPieces() {
        return capturedPieces;
    }

    public void setCapturedPieces(int capturedPieces) {
        this.capturedPieces = capturedPieces;
    }

    public int getPlayedPieces() {
        return playedPieces;
    }

    public void setPlayedPieces(int playedPieces) {
        this.playedPieces = playedPieces;
    }

    public boolean stillHavePieces() {
        if (mypieces <= 0) {
            return false;
        }
        return true;
    }

    public boolean addSelfToSpace(int i) {
        if (board[i] == -1) {
            board[i] = playerNumber;
            mypieces--;
            return true;
        } else {
            return false;
        }

    }

    public void setPlayerAtSpace(int space, int player) {
        board[space] = player;
    }

    public boolean isSpaceMine(int space) {
        if (board[space] == playerNumber)
            return true;
        else
            return false;
    }

    public boolean isSpaceEmpty(int space) {
        if (board[space] == -1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isMoveAllowed(int start, int end) {
        if ((end == start - 1) || (end == start - 5) || (end == start + 1) || (end == start + 5))
            return true;
        else
            return false;
    }

    public void movePlayer(int start, int end, int playerNumber) {
        board[start] = -1;
        board[end] = playerNumber;
    }
}
