package model.entities;

/**
 * Abstraction of a board. It holds the state of the board in the local instance of the client.
 * It can be updated in order to reflect the current state of the game
 */
public class Board {


    //total amount of pieces allowed for each player
    public static final int TOTAL_PIECES = 12;

    //nice constants to represent the colors of the pieces
    public static final int PIECE_COLOR_BLACK = 1;
    public static final int PIECE_COLOR_RED = 2;

    //nice constants to represent the situation after someone finishes the game
    public static final int SITUATION_TIE = 100;
    public static final int SITUATION_WON = 200;
    public static final int SITUATION_LOST = 300;

    //the number associated with a player
    private int mPlayerNumber;

    //the board is simply an array with number
    private int[] board;


    //total amount of pieces held by current player
    private int mMyPieceCount;

    //total amount of captured pieces held by the current player
    private int mCapturePiecesCount;

    //total amount of lost pieces from this player
    private int mLostPiecesCount;

    //this player's pieces colour
    private int mMyPiecesColor = -1;

    //total amount of pieces played by this player
    private int mPlayedPiecesCount;

    /**
     * Constructor
     *
     * @param playerNumber takes in a number given by the server to uniquely identify this player in the board
     */
    public Board(int playerNumber) {

        //strt up the board
        board = new int[30];

        //empty it
        for (int i = 0; i < 30; i++)
            board[i] = -1;

        //reset the variables with proper values
        this.mPlayerNumber = playerNumber;
        mMyPieceCount = TOTAL_PIECES;
        mCapturePiecesCount = 0;
        mPlayedPiecesCount = 0;

    }

    /**
     * Some getters and setters
     */
    public int getmMyPiecesColor() {
        return mMyPiecesColor;
    }

    public void setmMyPiecesColour(int mMyPiecesColor) {
        this.mMyPiecesColor = mMyPiecesColor;
    }

    public int getmPlayerNumber() {
        return mPlayerNumber;
    }

    public int getmMyPieceCount() {
        return mMyPieceCount;
    }

    public int getmCapturePiecesCount() {
        return mCapturePiecesCount;
    }

    public int getmLostPiecesCount() {
        return mLostPiecesCount;
    }

    public int getmPlayedPiecesCount() {
        return mPlayedPiecesCount;
    }

    /**
     * Checks if this player still has available pieces to play
     *
     * @return true if so
     */
    public boolean stillHavePieces() {
        if (mMyPieceCount <= 0) {
            return false;
        }
        return true;
    }


    /**
     * Adds this player to a space
     *
     * @param space the space to which add this client
     * @return true if space was empty and player was added, false otherwise
     */
    public boolean addSelfToSpace(int space) {
        if (board[space] == -1) {
            board[space] = mPlayerNumber;
            mMyPieceCount--;
            return true;
        } else {
            return false;
        }

    }

    /**
     * Add a given player to a board space
     *
     * @param space  the space to which the player will be added
     * @param player the player number
     */
    public void setPlayerAtSpace(int space, int player) {
        board[space] = player;
    }


    /**
     * Checks if a space belongs to this client
     *
     * @param space the space
     * @return true if so
     */
    public boolean isSpaceMine(int space) {
        if (board[space] == mPlayerNumber)
            return true;
        else
            return false;
    }

    /**
     * Checks if a space is empty
     *
     * @param space the space to check
     * @return true if so
     */
    public boolean isSpaceEmpty(int space) {
        if (board[space] == -1) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Checks if a certain move is allowed. An allowed move is a vertical or horizonal one space jump
     * or a two space jump with a capture.
     *
     * @param start space where moviment starts
     * @param end   space where moviment ends
     * @return true if allowed
     */
    public boolean isMoveAllowed(int start, int end) {
        //check if end position is one of the sorrounding spaces
        if ((end == start - 1) ||
                (end == start - 5) ||
                (end == start + 1) ||
                (end == start + 5)) {
            return true;
        } else {
            //check if end position is one of (two spaces jumped) sorrounding spaces
            if ((end == start - 2) ||
                    (end == start - 10) ||
                    (end == start + 2) ||
                    (end == start + 10)) {

                //if so, checks if there is a possibility of a capture
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

    /**
     * Checks if a capture is possible
     *
     * @param start start space
     * @param end   end space
     * @return true if there is a capture between start and end space, false otherwise
     */
    public boolean isCapturePossible(int start, int end) {

        //for a capture to happen the central space needs to have an oponents piece and it must not be empty
        boolean res = false;
        if (end == start + 2) {
            if ((board[start + 1] != mPlayerNumber) && !isSpaceEmpty(start + 1))
                res = true;

        }

        if (end == start - 2) {
            if ((board[start - 1] != mPlayerNumber) && !isSpaceEmpty(start - 1))
                res = true;
        }

        if (end == start + 10) {
            if ((board[start + 5] != mPlayerNumber) && !isSpaceEmpty(start + 5))
                res = true;
        }

        if (end == start - 10) {
            if ((board[start - 5] != mPlayerNumber) && !isSpaceEmpty(start - 5))
                res = true;
        }

        return res;
    }

    /**
     * Makes the necessary changes to represent a move from a player. SImply empty start space
     * and fill end space with the player number.
     *
     * @param start        the start position
     * @param end          the end position
     * @param playerNumber the player number
     */
    public void movePlayer(int start, int end, int playerNumber) {
        board[start] = -1;
        board[end] = playerNumber;
    }

    /**
     * Resets the board to initial conditions
     */
    public void restoreBoard() {
        for (int i = 0; i < 30; i++)
            board[i] = -1;

        mMyPieceCount = TOTAL_PIECES;
        mCapturePiecesCount = 0;
        mPlayedPiecesCount = 0;
        mLostPiecesCount = 0;

    }

    /**
     * Effectively capture a piece. Capturing client view.
     *
     * @param start the start position of the piece
     * @param end   the end position of the piece
     * @return the position of the captured piece
     */
    public int performCapture(int start, int end) {
        //finds out what was the space position of the captured piece
        int capturePiece = findOutPiecePosition(start, end);

        mCapturePiecesCount++;

        //empty space
        board[capturePiece] = -1;

        return capturePiece;

    }

    /**
     * Performs a capture. Captured client view.
     *
     * @param pos the position of the capture.
     */
    public void performCapture(int pos) {
        mLostPiecesCount++;
        board[pos] = -1;

    }

    /**
     * Discovers what is the position of the captured piece relative to the moviment end and start positions
     *
     * @param start the moviment start position
     * @param end   the moviment end position
     * @return the position of the captured piece relative to these two
     */
    private int findOutPiecePosition(int start, int end) {
        int res = -1;
        if (end == start + 2) {
            if ((board[start + 1] != mPlayerNumber) && !isSpaceEmpty(start + 1))
                res = start + 1;

        }

        if (end == start - 2) {
            if ((board[start - 1] != mPlayerNumber) && !isSpaceEmpty(start - 1))
                res = start - 1;
        }

        if (end == start + 10) {
            if ((board[start + 5] != mPlayerNumber) && !isSpaceEmpty(start + 5))
                res = start + 5;
        }

        if (end == start - 10) {
            if ((board[start - 5] != mPlayerNumber) && !isSpaceEmpty(start - 5))
                res = start - 5;
        }

        return res;
    }

    /**
     * Checks if capture is possible begining in a given space
     *
     * @param start the space from which a movement should begin to generate a capture
     * @return true if it is possible
     */
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

    /**
     * Check for the whole board to see if this client can still perform any captures
     *
     * @return true if so
     */
    public boolean isCaptureGenerallyPossible() {

        for (int i = 0; i < 30; i++) {
            if (board[i] == mPlayerNumber) {
                if (checkCapturePossibility(i)) {
                    return true;
                }
            }
        }


        return false;
    }

    /**
     * Removes a piece from a given space. Capturing client view.
     *
     * @param piece the space in which the piece is
     */
    public void performRemoval(int piece) {
        mCapturePiecesCount++;
        board[piece] = -1;
    }

    /**
     * Removes a piece from a given space. Captured client view.
     *
     * @param removedPos the space in which the piece is
     */
    public void performLost(int removedPos) {
        board[removedPos] = -1;
        mLostPiecesCount++;
    }

    /**
     * CHeks if this player has captured all pieces from the oponent
     *
     * @return true if so
     */
    public boolean hasCapturedAll() {

        if (mCapturePiecesCount == TOTAL_PIECES)
            return true;
        else
            return false;
    }

    /**
     * Checks if this plalyer has lost all pieces
     *
     * @return
     */
    public boolean hasLostAll() {
        if (mLostPiecesCount == TOTAL_PIECES)
            return true;
        else
            return false;
    }

    /**
     * Checks if oponent has any pieces on the board
     *
     * @return
     */
    public boolean oponentHasPiecesOnBoard() {

        boolean res = false;

        for (int i = 0; i < 30; i++) {
            if (board[i] != -1 && board[i] != mPlayerNumber) {
                res = true;
            }
        }
        return res;
    }

    /**
     * Get the piece color from the oponent
     *
     * @return a constant representing the piece color
     */
    public int getOtherPlayerPieceColor() {
        return (mMyPiecesColor == PIECE_COLOR_BLACK) ? PIECE_COLOR_RED : PIECE_COLOR_BLACK;
    }


    /**
     * Checks if the situation is of tie, win or lost
     *
     * @param openentPieces the number of pieces of the oponent in the board
     * @return
     */
    public int getFinishSituation(int openentPieces) {

        int myBoardPieces = mPlayedPiecesCount - mLostPiecesCount;

        if (openentPieces > 0 && myBoardPieces > 0 && openentPieces <= 3 && myBoardPieces <= 3)
            return SITUATION_TIE;

        return -1;
    }

    /**
     * If a tie did not happens, check the situation again
     *
     * @return
     */
    public int getAfterNotTieSituation() {
        if (mCapturePiecesCount > mLostPiecesCount)
            return SITUATION_WON;

        if (mLostPiecesCount > mCapturePiecesCount)
            return SITUATION_LOST;

        return SITUATION_TIE;
    }

    /**
     * Checks if this player is a loser
     *
     * @return true if so
     */
    public boolean isLoser() {
        return mLostPiecesCount > mCapturePiecesCount;
    }
}
