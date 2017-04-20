package model;

/**
 * Created by ellca on 20/04/2017.
 */
public class Board {

    public static int PLAYER1 = 0;
    public static int PLAYER2 = 1;

    private int playerNumber;
    int[][] board;

    public Board(int playerNumber) {
        board = new int[6][5];

        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++) {
                board[i][j] = -1;
            }

        this.playerNumber = playerNumber;
    }
}
