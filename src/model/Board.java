package model;

/**
 * Created by ellca on 20/04/2017.
 */
public class Board {

    public static final int TOTAL_PIECES = 5;

    private int playerNumber;

    private int[] board;

    private int mypieces;

    private int capturedPieces;

    private int lostPieces;


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

    public int getLostPieces() {
        return lostPieces;
    }

    public void setLostPieces(int lostPieces) {
        this.lostPieces = lostPieces;
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
        if ((end == start - 1) ||
                (end == start - 5) ||
                (end == start + 1) ||
                (end == start + 5)) {
            return true;
        } else {
            if ((end == start - 2) ||
                    (end == start - 10) ||
                    (end == start + 2) ||
                    (end == start + 10)) {

                if (isCapturePossible(start, end)) {
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
    }

    public boolean isCapturePossible(int start, int end) {

        boolean res = false;
        if (end == start + 2) {
            if ((board[start + 1] != playerNumber) && !isSpaceEmpty(start + 1))
                res = true;

        }

        if (end == start - 2) {
            if ((board[start - 1] != playerNumber) && !isSpaceEmpty(start - 1))
                res = true;
        }

        if (end == start + 10) {
            if ((board[start + 5] != playerNumber) && !isSpaceEmpty(start + 5))
                res = true;
        }

        if (end == start - 10) {
            if ((board[start - 5] != playerNumber) && !isSpaceEmpty(start - 5))
                res = true;
        }

        return res;
    }

    public void movePlayer(int start, int end, int playerNumber) {
        board[start] = -1;
        board[end] = playerNumber;
    }

    public void restoreBoard() {
        for (int i = 0; i < 30; i++)
            board[i] = -1;

        mypieces = TOTAL_PIECES;
        capturedPieces = 0;
        playedPieces = 0;
        lostPieces = 0;

    }

    public int performCapture(int start, int end) {
        int capturePiece = findOutPiecePosition(start, end);

        capturedPieces++;
        board[capturePiece] = -1;

        return capturePiece;

    }

    public void performCapture(int pos) {
        lostPieces++;

        board[pos] = -1;

    }

    private int findOutPiecePosition(int start, int end) {
        int res = -1;
        if (end == start + 2) {
            if ((board[start + 1] != playerNumber) && !isSpaceEmpty(start + 1))
                res = start + 1;

        }

        if (end == start - 2) {
            if ((board[start - 1] != playerNumber) && !isSpaceEmpty(start - 1))
                res = start - 1;
        }

        if (end == start + 10) {
            if ((board[start + 5] != playerNumber) && !isSpaceEmpty(start + 5))
                res = start + 5;
        }

        if (end == start - 10) {
            if ((board[start - 5] != playerNumber) && !isSpaceEmpty(start - 5))
                res = start - 5;
        }

        return res;
    }

    public boolean checkCapturePossibility(int start) {
        if (start < 28) {
            if (isCapturePossible(start, start + 2))
                return true;
        }

        if (start > 1) {
            if (isCapturePossible(start, start - 2))
                return true;
        }
        if (start < 20) {
            if (isCapturePossible(start, start + 10))
                return true;
        }
        if (start > 9) {
            if (isCapturePossible(start, start - 10))
                return true;
        }
        return false;
    }

    public boolean isCaptureGenerallyPossible() {

        for (int i = 0; i < 30; i++) {
            if (board[i] == playerNumber) {
                if (checkCapturePossibility(i)) {
                    return true;
                }
            }
        }


        return false;
    }

    public void performRemoval(int piece) {
        capturedPieces++;
        board[piece] = -1;
    }

    public void performLost(int removedPos) {
        board[removedPos] = -1;
        lostPieces++;
    }

    public boolean hasCapturedAll() {

        if (capturedPieces == TOTAL_PIECES)
            return true;
        else
            return false;
    }

    public boolean hasLostAll() {
        if (lostPieces == TOTAL_PIECES)
            return true;
        else
            return false;
    }

    public boolean oponentHasPiecesOnBoard() {

        boolean res = false;

        for (int i = 0; i < 30; i++) {
            if (board[i] != -1 && board[i] != playerNumber) {
                res = true;
            }
        }
        return res;
    }
}
