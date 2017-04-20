package model;

/**
 * Created by ellca on 20/04/2017.
 */
public class Board {

    public static int PLAYER1 = 0;
    public static int PLAYER2 = 1;

    private int playerNumber;

    private int[][] board;

    private int mypieces;

    private int capturedPieces;

    private int playedPieces;

    public Board(int playerNumber) {
        board = new int[6][5];

        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++) {
                board[i][j] = -1;
            }

        this.playerNumber = playerNumber;
        mypieces = 0;
        capturedPieces = 0;
        playedPieces = 0;

    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
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
}
