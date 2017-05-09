package view;

import presenter.ClientPresenter;
import model.entities.Board;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class that creates and manages the view of the client
 */
public class ClientWindow implements ActionListener, ClientView {

    //screen elements
    private JButton space1;
    private JButton space26;
    private JButton space7;
    private JButton space2;
    private JButton space17;
    private JButton space6;
    private JButton space21;
    private JButton space16;
    private JButton space11;
    private JButton space12;
    private JButton space22;
    private JButton space27;
    private JButton space3;
    private JButton space8;
    private JButton space13;
    private JButton space18;
    private JButton space23;
    private JButton space28;
    private JButton space4;
    private JButton space5;
    private JButton space9;
    private JButton space10;
    private JButton space14;
    private JButton space15;
    private JButton space19;
    private JButton space20;
    private JButton space24;
    private JButton space25;
    private JButton space29;
    private JButton space30;
    private JPanel jpMain;
    private JPanel jpBoard;
    private JPanel jpConnection;
    private JPanel jpChatArea;
    private JTextField tfMsgmToSend;
    private JButton jbSend;
    private JTextArea taReceiveArea;
    private JButton jbConnect;
    private JTextField tfIpAddress;
    private JTextField tfPort;
    private JCheckBox cbPlayLocal;
    private JCheckBox servidorCheckBox;
    private JLabel jlConnectionMessages;
    private JPanel jpIpInfo;
    private JLabel jlIP;
    private JLabel jlPort;
    private JTextField tfName;
    private JButton jbRestartGame;
    private JLabel jlTurn;
    private JButton jbStartGame;
    private JPanel jpStatus;
    private JPanel jpPieces;
    private JPanel jpCaptured;
    private JButton jbAddPiece;
    private JLabel jlHandPieces;
    private JLabel jlPlayedPieces;
    private JLabel jlCapturedPieces;
    private JPanel jpConnectionStatus;
    private JPanel jpPlayedPieces;
    private JLabel jlLostPieces;
    private JRadioButton rbRed;
    private JRadioButton rbBlack;
    private JButton jbFinishGame;

    //main window
    private JFrame mWindow;

    //the presenter
    private ClientPresenter mClientPresenter;

    //helper flags
    private boolean isBlockedForAdding = false; //inicates if clik should be blocked due to an adding piece action
    private boolean hasGameStarted = false; //indicates if a game has started
    private boolean isYourTurn = false; //indicates if it is this players turn
    private boolean shouldEndTurn = false; //indicate if a player has executed all allowed momvements and if he should end his turn
    private boolean hasStartedMove = false; //indicates if a move has started (clicked in the beginign space)
    private boolean hasFinishedMove = false; //indicates if a movement has finished
    private boolean hasPartnerGivenUp = false; //indicates if partner left game
    private boolean hasCapturedOnce = false; //indicates if he capture at least one piece in a turn
    private boolean isBlockedForElimination = false; //block clicks for elimination
    private boolean hasAllEntered = false; //indicates if all players have entered the room

    //holds the buttons in an array for easier access
    private ArrayList<JButton> buttons = new ArrayList<>();

    //holds the space moves
    private int[] move = new int[2];

    //holds the image for the icons
    private Icon iconBlack;
    private Icon iconRed;

    //the last position the player was in after a capture
    private int lastPositionAFterCapture = -1;

    //an action to send a chat message
    Action sendMsgAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = tfMsgmToSend.getText();
            taReceiveArea.append("You say -> " + msg + "\n");
            tfMsgmToSend.setText("");
            mClientPresenter.sendChatMessage(msg);
        }
    };


    //constructor
    public ClientWindow(JFrame frame) {
        mWindow = frame;

        //set up window action
        mWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //setup icons
        iconBlack = createImageIcon("assets/black.png", "the black icon");
        iconRed = createImageIcon("assets/red.png", "the red icon");

        //disable game options until connection happens
        enableGameOptions(false);

        //add action listener for certain buttons
        addActionListenerForButtons();

        //add board buttons to the array for easier access
        addButtonsToArray();

        //add action listener for the board button
        addActionListenerForBoardButtons();

        cbPlayLocal.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    tfIpAddress.setEnabled(false);
                    tfPort.setEnabled(false);
                } else {
                    tfIpAddress.setEnabled(true);
                    tfPort.setEnabled(true);
                }
            }
        });

        mWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (!hasAllEntered) System.exit(0);
                if (!hasPartnerGivenUp && hasGameStarted) {
                    int i = JOptionPane.showConfirmDialog(null, "If you close this screen, you'll give up this match");
                    if (i == 0) {
                        mClientPresenter.terminateClient();
                        System.exit(0);
                    }
                } else {
                    mClientPresenter.terminateClient();
                    System.exit(0);
                }
            }
        });

        ButtonGroup rbButtons = new ButtonGroup();
        rbButtons.add(rbRed);
        rbButtons.add(rbBlack);


        jbFinishGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasGameStarted)
                    if (isYourTurn) {
                        mClientPresenter.performFinishGame();
                    }
            }
        });
    }

    /**
     * Add all board buttons to the array for easier access
     */
    private void addButtonsToArray() {
        buttons.add(space1);
        buttons.add(space2);
        buttons.add(space3);
        buttons.add(space4);
        buttons.add(space5);
        buttons.add(space6);
        buttons.add(space7);
        buttons.add(space8);
        buttons.add(space9);
        buttons.add(space10);
        buttons.add(space11);
        buttons.add(space12);
        buttons.add(space13);
        buttons.add(space14);
        buttons.add(space15);
        buttons.add(space16);
        buttons.add(space17);
        buttons.add(space18);
        buttons.add(space19);
        buttons.add(space20);
        buttons.add(space21);
        buttons.add(space22);
        buttons.add(space23);
        buttons.add(space24);
        buttons.add(space25);
        buttons.add(space26);
        buttons.add(space27);
        buttons.add(space28);
        buttons.add(space29);
        buttons.add(space30);
    }

    /**
     * Add action listener for board buttons, this class implements the action listener
     */
    private void addActionListenerForBoardButtons() {

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).addActionListener(this);
        }

    }

    /**
     * Tries to add a piece to a given space
     * @param space the space to which the adding will happen
     * @return true if sucessfully
     */
    private boolean tryAddingToSpace(int space) {
        return mClientPresenter.performAddToSpace(space);
    }

    /**
     * Finish adding a piece
     */
    private void finishedAdding() {
        shouldEndTurn = true;
        isBlockedForAdding = false;
        jbAddPiece.setText("Add New Piece");


        jbAddPiece.setEnabled(false);

    }

    /**
     * Add action listener for some buttons on the screen
     */
    private void addActionListenerForButtons() {

        jbRestartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mClientPresenter.performResart();
            }
        });

        jbConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!tfName.getText().isEmpty()) {
                    if (cbPlayLocal.isSelected())
                        mClientPresenter.startUpClient(tfName.getText());
                    else {
                        if (tfIpAddress.getText().isEmpty() || tfPort.getText().isEmpty()) {
                            JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Type in the server information: IP and Port");
                        } else {
                            int port = -1;
                            try {
                                port = Integer.parseInt(tfPort.getText());
                            } catch (NumberFormatException ne) {
                            }

                            if (validIP(tfIpAddress.getText()) && port != -1)
                                mClientPresenter.startUpClient(tfName.getText(), tfIpAddress.getText(), port);
                            else
                                JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Invalid Port or IP");

                        }
                    }
                } else
                    JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Please, type in your name");


            }
        });


        jbSend.addActionListener(sendMsgAction);

        tfMsgmToSend.addActionListener(sendMsgAction);

        jbStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!hasGameStarted) {
                    int piece;

                    if (rbBlack.isSelected())
                        piece = Board.PIECE_COLOR_BLACK;
                    else {
                        piece = Board.PIECE_COLOR_RED;
                        rbRed.setSelected(true);
                    }
                    mClientPresenter.performStartMatch(piece);
                    jbStartGame.setText("End Turn");
                    hasGameStarted = true;
                    hasPartnerGivenUp = false;
                } else {
                    mClientPresenter.performEndMyTurn();
                    shouldEndTurn = false;
                    hasCapturedOnce = false;
                    lastPositionAFterCapture = -1;
                }
            }
        });

        jbAddPiece.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (shouldEndTurn) {
                    JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Max number of moves reached. Please end your turn.");
                } else {
                    if (isBlockedForAdding) {
                        isBlockedForAdding = false;
                        jbAddPiece.setText("Add New Piece");

                    } else {
                        isBlockedForAdding = true;
                        jbAddPiece.setText("Cancel Adding");
                    }
                }
            }
        });
    }

    private void createUIComponents() {

    }

    /**
     * Starts this up
     */
    public void startUp() {
        mClientPresenter = new ClientPresenter(this);
    }

    /**
     * Show a message for the connection
     * @param msg the message
     */
    @Override
    public void connectionMessage(String msg) {
        jlConnectionMessages.setText(msg);
    }

    /**
     * When a user is connected
     */
    @Override
    public void onUserConnected() {

        enableConnectionOptions(false);
        enableGameOptions(true);
    }

    /**
     * When a chat message is received
     * @param mRcv the message received
     */
    @Override
    public void receivedMessage(String mRcv) {
        taReceiveArea.append(mRcv + "\n");
    }

    /**
     * When a connection error happens
     */
    @Override
    public void showConnectionError() {
        jlConnectionMessages.setText("Server down");
    }

    /**
     * Clears the board
     */
    @Override
    public void emptyBoard() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setIcon(null);
            buttons.get(i).setText("");
        }

    }


    /**
     * Set turn  for a certain player. Show necessary things on the screen.
     * @param sender
     */
    @Override
    public void setTurnPlayer(String sender) {

        jlTurn.setText("Turn: " + sender);
        jbStartGame.setEnabled(false);
        jbAddPiece.setEnabled(false);
        jbFinishGame.setEnabled(false);
        isYourTurn = false;
    }

    /**
     * Set your turn
     */
    @Override
    public void setYourTurn() {
        jlTurn.setText("Your turn");
        jbStartGame.setEnabled(true);
        if (mClientPresenter.hasPieces())
            jbAddPiece.setEnabled(true);
        isYourTurn = true;
        jbFinishGame.setEnabled(true);
    }

    /**
     * Changes the UI to relfelct a start of a fame
     * @param piece the piece color of this user
     */
    @Override
    public void setGameStarted(int piece) {
        hasGameStarted = true;
        jbStartGame.setText("End Turn");
        jbStartGame.setEnabled(false);
        isYourTurn = false;
        if (piece == Board.PIECE_COLOR_RED)
            rbRed.setSelected(true);
        else
            rbBlack.setSelected(true);
        disablePieceSelectors();
    }


    /**
     * Adds a player to a certain space
     * @param i the space
     * @param playerNumber player number
     * @param pieceColor piece color
     */
    @Override
    public void addPlayerToSpace(int i, int playerNumber, int pieceColor) {
        //buttons.get(i).setText(playerNumber + "");

        if (pieceColor == Board.PIECE_COLOR_RED) {
            buttons.get(i).setIcon(iconRed);
        } else {
            buttons.get(i).setIcon(iconBlack);
        }
    }

    /**
     * Updates the piece count in the screen
     * @param hand the amount of pieces in your hand
     */
    @Override
    public void showMyPiecesNumber(int hand) {
        jlHandPieces.setText(hand + "");
        jlPlayedPieces.setText((Board.TOTAL_PIECES - hand) + "");
    }

    /**
     * Shows a move in the screen
     * @param start start space
     * @param end end space
     * @param playerNumber player number
     * @param pieceColor piece color
     */
    @Override
    public void move(int start, int end, int playerNumber, int pieceColor) {
        //buttons.get(start).setText("Vazio");
        buttons.get(start).setIcon(null);
        //buttons.get(end).setText(playerNumber + "");
        if (pieceColor == Board.PIECE_COLOR_RED) {
            buttons.get(end).setIcon(iconRed);
        } else {
            buttons.get(end).setIcon(iconBlack);
        }
    }

    /**
     * SHows a message anoucning that the partner left
     * @param partnerName the partnet name
     */
    @Override
    public void warnPartnerLeft(String partnerName) {
        JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Player " + partnerName + " has left the match, you won!");
        hasPartnerGivenUp = true;
        restoreBoard();
        mClientPresenter.restoreBoard();
    }

    /**
     * Show message stating the partner asked for a reset
     * @param partnerName the partner name
     */
    @Override
    public void partnerAskForRestart(String partnerName) {
        int dialogResult = JOptionPane.showConfirmDialog($$$getRootComponent$$$(), "Player " + partnerName + " is requesting a restart. Do you accept?");
        if (dialogResult == JOptionPane.YES_OPTION) {
            mClientPresenter.performAcceptRestart();
        } else {
            mClientPresenter.refuseRestart();
        }

    }

    /**
     * Restarts the board
     */
    @Override
    public void restoreBoard() {
        emptyBoard();
        jbStartGame.setText("Start Game");
        jlTurn.setText("");
        jbStartGame.setEnabled(true);
        jbAddPiece.setEnabled(false);
        jbFinishGame.setEnabled(false);

        isBlockedForAdding = false;
        hasGameStarted = false;
        isYourTurn = false;
        shouldEndTurn = false;
        hasStartedMove = false;
        hasFinishedMove = false;
        hasPartnerGivenUp = false;
        hasCapturedOnce = false;
        isBlockedForElimination = false;
        lastPositionAFterCapture = -1;
        move[0] = move[1] = -1;

    }

    /**
     * Performs a capture
     * @param capturedPos the position captured
     */
    @Override
    public void performCapture(int capturedPos) {
        //buttons.get(capturedPos).setText("Vazio");
        buttons.get(capturedPos).setIcon(null);

    }

    @Override
    public void updateCapturedPiecesCount(int capturedPieces) {
        jlCapturedPieces.setText(capturedPieces + "");
    }

    @Override
    public void updateLostPiecesCount(int lostPieces) {
        jlLostPieces.setText(lostPieces + "");
    }

    @Override
    public void anounceYouWin() {
        JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Congratulations, you won!");
        mClientPresenter.restoreBoard();
        restoreBoard();

    }

    @Override
    public void anounceYouLost() {
        JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Sorry, you lost!");
        mClientPresenter.restoreBoard();
        restoreBoard();
    }

    @Override
    public void returnNotStartedState() {
        jbStartGame.setText("Start Game");
        hasGameStarted = false;
        hasPartnerGivenUp = false;
    }

    @Override
    public void setAllEntered() {
        hasAllEntered = true;
    }

    /**
     * ANoucne the leaving of the server
     */
    @Override
    public void serverLeft() {
        jlConnectionMessages.setText("Server down.");
    }

    /**
     * Reset the connection state
     */
    @Override
    public void returnToUnconnectedState() {
        hasAllEntered = false;
        emptyBoard();
        enableConnectionOptions(true);
        enableGameOptions(false);
        mClientPresenter.restoreBoard();
        restoreTurnOptions();
    }

    /**
     * Disable piece selectors
     */
    @Override
    public void disablePieceSelectors() {
        rbBlack.setEnabled(false);
        rbRed.setEnabled(false);
    }

    /**
     * Anounce a tie on the screen
     */
    @Override
    public void anounceTie() {
        JOptionPane.showMessageDialog($$$getRootComponent$$$(), "It's a tie!");
        mClientPresenter.restoreBoard();
        restoreBoard();
    }

    /**
     * Enable the connection options
     * @param en
     */
    private void enableConnectionOptions(boolean en) {
        jpIpInfo.setEnabled(en);
        tfPort.setEnabled(en);
        tfIpAddress.setEnabled(en);
        jlIP.setEnabled(en);
        jlPort.setEnabled(en);
        jbConnect.setEnabled(en);
        cbPlayLocal.setEnabled(en);
        tfName.setEnabled(en);

    }

    /**
     * Enables the game options
     * @param en
     */
    private void enableGameOptions(boolean en) {
        jbStartGame.setEnabled(en);
        jbRestartGame.setEnabled(en);

        jbSend.setEnabled(en);
        rbBlack.setEnabled(en);
        rbRed.setEnabled(en);
        jbFinishGame.setEnabled(en);
    }

    /**
     * Clear the options in the controle de turno screen
     */
    private void restoreTurnOptions() {
        isYourTurn = false;
        jlTurn.setText("Turn control");
        jbAddPiece.setEnabled(false);
        jbStartGame.setText("Start Game");
        jbFinishGame.setEnabled(false);
    }

    /**
     * Catches all button cliks on the board buttons
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();
        int buttonPos = buttons.indexOf(button);

        if (isYourTurn) {

            if (!isBlockedForElimination) {
                onNotEliminating(buttonPos);
            } else {
                onEliminating(buttonPos);
            }
        }
    }

    /**
     * If an elimination is not happening
     * @param buttonPos the position clicked by the user
     */
    private void onNotEliminating(int buttonPos) {
        if (hasCapturedOnce && !mClientPresenter.canStillCapture()) {
            shouldEndTurn = true;
        }

        if (!shouldEndTurn) {
            if (isBlockedForAdding) {
                if (tryAddingToSpace(buttonPos))
                    finishedAdding();
                else
                    JOptionPane.showMessageDialog($$$getRootComponent$$$(), "This space is busy. Choose another one.");
            } else {

                tryToMove(buttonPos);
            }
        } else {
            JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Max number of moves reached. Please end your turn.");
        }
    }

    /**
     * When elimination is on
     * @param buttonPos the position clicked by the user
     */
    private void onEliminating(int buttonPos) {
        if (!mClientPresenter.isSpaceEmpty(buttonPos)) {
            if (!mClientPresenter.isSpaceMine(buttonPos)) {
                mClientPresenter.performRemovePiece(buttonPos);
                isBlockedForElimination = false;
            } else {
                JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Select another player's piece.");
            }
        } else {
            JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Space empty! Select another player's piece.");
        }
    }


    /**
     * Tries to move from a location
     * @param space the startiing space
     */
    private void tryToMove(int space) {
        if (!hasStartedMove) {
            if (hasCapturedOnce && mClientPresenter.checkCapturePossibility(space))
                onNotStartedMove(space);
            else {
                if (!hasCapturedOnce)
                    onNotStartedMove(space);
                else
                    JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Movement not allowed.");
            }
        } else {
            onStartedMove(space);
        }

        if (hasFinishedMove) {
            onFinishedMove();

        }
    }

    /**
     * When a move hasnet started yet
     * @param space the space where will start
     */
    private void onNotStartedMove(int space) {
        if (mClientPresenter.isSpaceMine(space)) {
            move[0] = space;
            hasStartedMove = true;
            jbAddPiece.setEnabled(false);
        } else {
            hasStartedMove = false;
            if (!mClientPresenter.isSpaceEmpty(space))
                JOptionPane.showMessageDialog($$$getRootComponent$$$(), "This piece is not yours.");
        }
    }

    /**
     * When a move has started
     * @param space the space where started
     */
    private void onStartedMove(int space) {
        if (mClientPresenter.isSpaceEmpty(space)) {
            move[1] = space;
            lastPositionAFterCapture = space;
            hasFinishedMove = true;
        } else {
            hasStartedMove = false;
            JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Space not empty.");
        }
    }

    /**
     * When finished a move
     */
    private void onFinishedMove() {
        if (mClientPresenter.tryToMove(move)) {
            hasStartedMove = false;
            hasFinishedMove = false;

            if (mClientPresenter.hasCapture(move)) {
                mClientPresenter.performCapture(move);
                hasCapturedOnce = true;
                if (mClientPresenter.oponentHasPiecesOnBoard()) {
                    JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Select an oponent's piece to be removed.");
                    isBlockedForElimination = true;
                }
            } else {
                shouldEndTurn = true;
            }

        } else {
            hasStartedMove = false;
            hasFinishedMove = false;
            JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Movement not allowed.");
        }
    }

    /**
     * Tries to create an image icon
     * @param path the path of the file
     * @param description a description
     * @return an image icon if created, null if not
     */
    protected ImageIcon createImageIcon(String path,
                                        String description) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Validates an ip string
     * @param ip the ip string
     * @return true if it is a valid ip
     */
    public static boolean validIP(String ip) {
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                return false;
            }

            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
            if (ip.endsWith(".")) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        jpMain = new JPanel();
        jpMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 6, new Insets(0, 0, 0, 0), -1, -1));
        jpMain.setName("PPD - Sockets");
        jpMain.setPreferredSize(new Dimension(1000, 800));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        jpMain.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(762, 356), null, 0, false));
        jpBoard = new JPanel();
        jpBoard.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 5, new Insets(0, 0, 0, 0), -1, -1));
        jpBoard.setEnabled(true);
        panel1.add(jpBoard, BorderLayout.CENTER);
        jpBoard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Game Area"));
        space26 = new JButton();
        space26.setText("26");
        jpBoard.add(space26, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space6 = new JButton();
        space6.setText("6");
        jpBoard.add(space6, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space21 = new JButton();
        space21.setText("21");
        jpBoard.add(space21, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space16 = new JButton();
        space16.setText("16");
        jpBoard.add(space16, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space11 = new JButton();
        space11.setText("11");
        jpBoard.add(space11, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space2 = new JButton();
        space2.setText("2");
        jpBoard.add(space2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space7 = new JButton();
        space7.setText("7");
        jpBoard.add(space7, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space12 = new JButton();
        space12.setText("12");
        jpBoard.add(space12, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space22 = new JButton();
        space22.setText("22");
        jpBoard.add(space22, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space27 = new JButton();
        space27.setText("27");
        jpBoard.add(space27, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space3 = new JButton();
        space3.setText("3");
        jpBoard.add(space3, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space13 = new JButton();
        space13.setText("13");
        jpBoard.add(space13, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space18 = new JButton();
        space18.setText("18");
        jpBoard.add(space18, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space23 = new JButton();
        space23.setText("23");
        jpBoard.add(space23, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space28 = new JButton();
        space28.setText("28");
        jpBoard.add(space28, new com.intellij.uiDesigner.core.GridConstraints(5, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space4 = new JButton();
        space4.setText("4");
        jpBoard.add(space4, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space5 = new JButton();
        space5.setText("5");
        jpBoard.add(space5, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space9 = new JButton();
        space9.setText("9");
        jpBoard.add(space9, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space10 = new JButton();
        space10.setText("10");
        jpBoard.add(space10, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space14 = new JButton();
        space14.setText("14");
        jpBoard.add(space14, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space15 = new JButton();
        space15.setText("15");
        jpBoard.add(space15, new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space19 = new JButton();
        space19.setText("19");
        jpBoard.add(space19, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space20 = new JButton();
        space20.setText("20");
        jpBoard.add(space20, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space24 = new JButton();
        space24.setText("24");
        jpBoard.add(space24, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space25 = new JButton();
        space25.setText("25");
        jpBoard.add(space25, new com.intellij.uiDesigner.core.GridConstraints(4, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space29 = new JButton();
        space29.setText("29");
        jpBoard.add(space29, new com.intellij.uiDesigner.core.GridConstraints(5, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space30 = new JButton();
        space30.setText("30");
        jpBoard.add(space30, new com.intellij.uiDesigner.core.GridConstraints(5, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space8 = new JButton();
        space8.setText("8");
        jpBoard.add(space8, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space1 = new JButton();
        space1.setText("1");
        jpBoard.add(space1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        space17 = new JButton();
        space17.setText("17");
        jpBoard.add(space17, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 70), new Dimension(140, 70), new Dimension(140, 70), 0, false));
        jpConnection = new JPanel();
        jpConnection.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        jpConnection.setFont(new Font("Montserrat SemiBold", jpConnection.getFont().getStyle(), jpConnection.getFont().getSize()));
        jpMain.add(jpConnection, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 5, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(34, 73), null, 0, false));
        jpConnection.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Connection", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Montserrat SemiBold", jpConnection.getFont().getStyle(), 22)));
        jpIpInfo = new JPanel();
        jpIpInfo.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        jpConnection.add(jpIpInfo, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpIpInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Connection Information", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Montserrat", jpIpInfo.getFont().getStyle(), 18)));
        tfIpAddress = new JTextField();
        tfIpAddress.setFont(new Font("Montserrat", tfIpAddress.getFont().getStyle(), tfIpAddress.getFont().getSize()));
        jpIpInfo.add(tfIpAddress, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jbConnect = new JButton();
        jbConnect.setAlignmentY(0.0f);
        jbConnect.setBorderPainted(true);
        jbConnect.setFont(new Font("Montserrat", jbConnect.getFont().getStyle(), jbConnect.getFont().getSize()));
        jbConnect.setText("Connect");
        jpIpInfo.add(jbConnect, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfPort = new JTextField();
        tfPort.setFont(new Font("Montserrat", tfPort.getFont().getStyle(), tfPort.getFont().getSize()));
        jpIpInfo.add(tfPort, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfName = new JTextField();
        tfName.setFont(new Font("Montserrat", tfName.getFont().getStyle(), tfName.getFont().getSize()));
        jpIpInfo.add(tfName, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cbPlayLocal = new JCheckBox();
        cbPlayLocal.setFont(new Font("Montserrat", cbPlayLocal.getFont().getStyle(), cbPlayLocal.getFont().getSize()));
        cbPlayLocal.setText("Play Local");
        jpIpInfo.add(cbPlayLocal, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jlPort = new JLabel();
        jlPort.setFont(new Font("Montserrat", jlPort.getFont().getStyle(), jlPort.getFont().getSize()));
        jlPort.setText("Port");
        jpIpInfo.add(jlPort, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jlIP = new JLabel();
        jlIP.setEnabled(true);
        jlIP.setFont(new Font("Montserrat", jlIP.getFont().getStyle(), jlIP.getFont().getSize()));
        jlIP.setText("IP");
        jpIpInfo.add(jlIP, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setFont(new Font("Montserrat", label1.getFont().getStyle(), label1.getFont().getSize()));
        label1.setText("Name");
        jpIpInfo.add(label1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jpConnectionStatus = new JPanel();
        jpConnectionStatus.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jpConnection.add(jpConnectionStatus, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(90, 217), null, 0, false));
        jpConnectionStatus.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Connection Status", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Montserrat", jpConnectionStatus.getFont().getStyle(), 18)));
        jlConnectionMessages = new JLabel();
        jlConnectionMessages.setFont(new Font("Montserrat", jlConnectionMessages.getFont().getStyle(), jlConnectionMessages.getFont().getSize()));
        jlConnectionMessages.setText("Desconectado");
        jpConnectionStatus.add(jlConnectionMessages, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        jpMain.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(14, 8), null, 0, false));
        jpChatArea = new JPanel();
        jpChatArea.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        jpMain.add(jpChatArea, new com.intellij.uiDesigner.core.GridConstraints(5, 2, 2, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpChatArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Chat Area", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Montserrat SemiBold", jpChatArea.getFont().getStyle(), 22)));
        tfMsgmToSend = new JTextField();
        tfMsgmToSend.setFont(new Font("Montserrat", tfMsgmToSend.getFont().getStyle(), tfMsgmToSend.getFont().getSize()));
        jpChatArea.add(tfMsgmToSend, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(true);
        jpChatArea.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        taReceiveArea = new JTextArea();
        taReceiveArea.setAutoscrolls(false);
        taReceiveArea.setEditable(false);
        taReceiveArea.setFont(new Font("Montserrat", taReceiveArea.getFont().getStyle(), 16));
        taReceiveArea.setLineWrap(true);
        taReceiveArea.setRequestFocusEnabled(true);
        taReceiveArea.setRows(5);
        scrollPane1.setViewportView(taReceiveArea);
        jbSend = new JButton();
        jbSend.setFont(new Font("Montserrat", jbSend.getFont().getStyle(), jbSend.getFont().getSize()));
        jbSend.setText("Send");
        jpChatArea.add(jbSend, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jbRestartGame = new JButton();
        jbRestartGame.setFont(new Font("Montserrat", jbRestartGame.getFont().getStyle(), jbRestartGame.getFont().getSize()));
        jbRestartGame.setText("Restart Game");
        jpMain.add(jbRestartGame, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(167, 8), null, 0, false));
        jbStartGame = new JButton();
        jbStartGame.setFont(new Font("Montserrat", jbStartGame.getFont().getStyle(), jbStartGame.getFont().getSize()));
        jbStartGame.setText("Start Game");
        jpMain.add(jbStartGame, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rbRed = new JRadioButton();
        rbRed.setText("Red");
        jpMain.add(rbRed, new com.intellij.uiDesigner.core.GridConstraints(1, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rbBlack = new JRadioButton();
        rbBlack.setText("Black");
        jpMain.add(rbBlack, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jbFinishGame = new JButton();
        jbFinishGame.setFont(new Font("Montserrat", jbFinishGame.getFont().getStyle(), jbFinishGame.getFont().getSize()));
        jbFinishGame.setText("Finish Game");
        jpMain.add(jbFinishGame, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jpStatus = new JPanel();
        jpStatus.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        jpMain.add(jpStatus, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpStatus.setBorder(BorderFactory.createTitledBorder(null, "Game Status", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Montserrat SemiBold", jpStatus.getFont().getStyle(), 22)));
        jpPieces = new JPanel();
        jpPieces.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jpStatus.add(jpPieces, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpPieces.setBorder(BorderFactory.createTitledBorder(null, "Not Played", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Montserrat", jpPieces.getFont().getStyle(), 18)));
        jlHandPieces = new JLabel();
        jlHandPieces.setFont(new Font("Montserrat", jlHandPieces.getFont().getStyle(), jlHandPieces.getFont().getSize()));
        jlHandPieces.setText("12");
        jpPieces.add(jlHandPieces, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jpStatus.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(null, "Lost ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Montserrat", panel2.getFont().getStyle(), 18)));
        jlLostPieces = new JLabel();
        jlLostPieces.setFont(new Font("Montserrat", jlLostPieces.getFont().getStyle(), jlLostPieces.getFont().getSize()));
        jlLostPieces.setText("0");
        panel2.add(jlLostPieces, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jpPlayedPieces = new JPanel();
        jpPlayedPieces.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jpStatus.add(jpPlayedPieces, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpPlayedPieces.setBorder(BorderFactory.createTitledBorder(null, "Played ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Montserrat", jpPlayedPieces.getFont().getStyle(), 18)));
        jlPlayedPieces = new JLabel();
        jlPlayedPieces.setFont(new Font("Montserrat", jlPlayedPieces.getFont().getStyle(), jlPlayedPieces.getFont().getSize()));
        jlPlayedPieces.setText("0");
        jpPlayedPieces.add(jlPlayedPieces, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jpCaptured = new JPanel();
        jpCaptured.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jpStatus.add(jpCaptured, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpCaptured.setBorder(BorderFactory.createTitledBorder(null, "Captured ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Montserrat", jpCaptured.getFont().getStyle(), 18)));
        jlCapturedPieces = new JLabel();
        jlCapturedPieces.setFont(new Font("Montserrat", jlCapturedPieces.getFont().getStyle(), jlCapturedPieces.getFont().getSize()));
        jlCapturedPieces.setText("0");
        jpCaptured.add(jlCapturedPieces, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jbAddPiece = new JButton();
        jbAddPiece.setEnabled(false);
        jbAddPiece.setFont(new Font("Montserrat", jbAddPiece.getFont().getStyle(), jbAddPiece.getFont().getSize()));
        jbAddPiece.setText("Add New Piece");
        jpStatus.add(jbAddPiece, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jlTurn = new JLabel();
        jlTurn.setFont(new Font("Montserrat SemiBold", jlTurn.getFont().getStyle(), 22));
        jlTurn.setText("Controle de turno");
        jpMain.add(jlTurn, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setFont(new Font("Montserrat", label2.getFont().getStyle(), label2.getFont().getSize()));
        label2.setText("Piece color");
        jpMain.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jpMain;
    }
}
