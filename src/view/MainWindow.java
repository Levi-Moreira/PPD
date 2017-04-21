package view;

import Presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by ellca on 12/04/2017.
 */
public class MainWindow implements MainView {
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
    private JButton jbYield;
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

    private Presenter presenter;

    private boolean blockedForAdding = false;

    private boolean gameStarted = false;

    private boolean yourTurn = false;

    private boolean shouldEndTurn = false;

    private ArrayList<JButton> buttons = new ArrayList<>();


    public MainWindow() {

        addActionListenerForButtons();
        addActionListenerForBoardButtons();
        addButtonsToArray();
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


    }

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

    private void addActionListenerForBoardButtons() {
        space1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yourTurn) {
                    if (blockedForAdding && !shouldEndTurn) {
                        if (tryAddingToSpace(1))
                            finishedAdding();
                        else
                            JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Espaço ocupado. Escolha outro.");
                    }
                }
            }
        });

        space2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yourTurn) {
                    if (blockedForAdding && !shouldEndTurn) {
                        if (tryAddingToSpace(2))
                            finishedAdding();
                        else
                            JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Espaço ocupado. Escolha outro.");
                    }
                }
            }
        });

        space3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yourTurn) {
                    if (blockedForAdding && !shouldEndTurn) {
                        if (tryAddingToSpace(3))
                            finishedAdding();
                        else
                            JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Espaço ocupado. Escolha outro.");
                    }
                }
            }
        });

        space4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yourTurn) {
                    if (blockedForAdding && !shouldEndTurn) {
                        if (tryAddingToSpace(4))
                            finishedAdding();
                        else
                            JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Espaço ocupado. Escolha outro.");
                    }
                }
            }
        });

        space5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yourTurn) {
                    if (blockedForAdding && !shouldEndTurn) {
                        if (tryAddingToSpace(5))
                            finishedAdding();
                        else
                            JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Espaço ocupado. Escolha outro.");
                    }
                }
            }
        });
    }

    private boolean tryAddingToSpace(int i) {
        return presenter.addToSpace(i);
    }

    private void finishedAdding() {
        shouldEndTurn = true;
        blockedForAdding = false;
        jbAddPiece.setText("Add New Piece");
    }

    private void addActionListenerForButtons() {
        jbConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!tfName.getText().isEmpty())
                    presenter.startUpClient(tfName.getText());
                else
                    JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Por favor, insira seu nome");


            }
        });


        jbSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = tfMsgmToSend.getText();
                taReceiveArea.append("Mensagem Enviada:" + msg + "\n");
                presenter.sendMessage(msg);
            }
        });

        jbStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameStarted) {
                    presenter.warnStartMach();
                    jbStartGame.setText("End Turn");
                    gameStarted = true;
                } else {
                    presenter.endMyTurn();
                }
            }
        });

        jbAddPiece.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (shouldEndTurn) {
                    JOptionPane.showMessageDialog($$$getRootComponent$$$(), "Você já realizou o número máximo de ações por jogada. Encerre sua vez.");
                } else {
                    if (blockedForAdding) {
                        blockedForAdding = false;
                        jbAddPiece.setText("Add New Piece");

                    } else {
                        blockedForAdding = true;
                        jbAddPiece.setText("Cancel Adding");
                    }
                }
            }
        });
    }

    private void createUIComponents() {


    }

    public void startUp() {
        presenter = new Presenter(this);
    }

    @Override
    public void connectionMessage(String msg) {
        jlConnectionMessages.setText(msg);
    }

    @Override
    public void onUserConnected() {
        enableAllIpOptions(false);
    }

    @Override
    public void receivedMessage(String mRcv) {
        taReceiveArea.append(mRcv + "\n");
    }

    @Override
    public void showConnectionError() {
        jlConnectionMessages.setText("Servidor indisponível");
    }

    @Override
    public void emptyBoard() {
        space1.setText("Vazio");
        space2.setText("Vazio");
        space3.setText("Vazio");
        space4.setText("Vazio");
        space5.setText("Vazio");
        space6.setText("Vazio");
        space7.setText("Vazio");
        space8.setText("Vazio");
        space9.setText("Vazio");
        space10.setText("Vazio");
        space11.setText("Vazio");
        space12.setText("Vazio");
        space13.setText("Vazio");
        space14.setText("Vazio");
        space15.setText("Vazio");
        space16.setText("Vazio");
        space17.setText("Vazio");
        space18.setText("Vazio");
        space19.setText("Vazio");
        space20.setText("Vazio");
        space21.setText("Vazio");
        space22.setText("Vazio");
        space23.setText("Vazio");
        space24.setText("Vazio");
        space25.setText("Vazio");
        space26.setText("Vazio");
        space27.setText("Vazio");
        space28.setText("Vazio");
        space29.setText("Vazio");
        space30.setText("Vazio");
    }


    @Override
    public void enableBoard(boolean en) {
        space1.setEnabled(en);
        space2.setEnabled(en);
        space3.setEnabled(en);
        space4.setEnabled(en);
        space5.setEnabled(en);
        space6.setEnabled(en);
        space7.setEnabled(en);
        space8.setEnabled(en);
        space9.setEnabled(en);
        space10.setEnabled(en);
        space11.setEnabled(en);
        space12.setEnabled(en);
        space13.setEnabled(en);
        space14.setEnabled(en);
        space15.setEnabled(en);
        space16.setEnabled(en);
        space17.setEnabled(en);
        space18.setEnabled(en);
        space19.setEnabled(en);
        space20.setEnabled(en);
        space21.setEnabled(en);
        space22.setEnabled(en);
        space23.setEnabled(en);
        space24.setEnabled(en);
        space25.setEnabled(en);
        space26.setEnabled(en);
        space27.setEnabled(en);
        space28.setEnabled(en);
        space29.setEnabled(en);
        space30.setEnabled(en);
    }

    @Override
    public void setTurnPlayer(String sender) {

        jlTurn.setText("É a vez de: " + sender);
        jbStartGame.setEnabled(false);
        jbAddPiece.setEnabled(false);
        yourTurn = false;
    }

    @Override
    public void setYourTurn() {
        jlTurn.setText("É a sua vez");
        jbStartGame.setEnabled(true);
        jbAddPiece.setEnabled(true);
        yourTurn = true;
    }

    @Override
    public void setGameStarted() {
        gameStarted = true;
        jbStartGame.setText("End Turn");
        jbStartGame.setEnabled(false);
        yourTurn = false;
    }

    @Override
    public void addPlayerToSpace(int i, int playerNumber) {
        buttons.get(i - 1).setText(playerNumber + "");
    }

    private void enableAllIpOptions(boolean en) {
        jpIpInfo.setEnabled(en);
        tfPort.setEnabled(en);
        tfIpAddress.setEnabled(en);
        jlIP.setEnabled(en);
        jlPort.setEnabled(en);
        jbConnect.setEnabled(en);
        cbPlayLocal.setEnabled(en);
        tfName.setEnabled(en);
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
        jpMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        jpMain.setName("PPD - Sockets");
        jpMain.setPreferredSize(new Dimension(1000, 800));
        jpMain.setBorder(BorderFactory.createTitledBorder("Yoté"));
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
        jpBoard.add(space26, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space6 = new JButton();
        space6.setText("6");
        jpBoard.add(space6, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space21 = new JButton();
        space21.setText("21");
        jpBoard.add(space21, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space16 = new JButton();
        space16.setText("16");
        jpBoard.add(space16, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space11 = new JButton();
        space11.setText("11");
        jpBoard.add(space11, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space2 = new JButton();
        space2.setText("2");
        jpBoard.add(space2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space7 = new JButton();
        space7.setText("7");
        jpBoard.add(space7, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space12 = new JButton();
        space12.setText("12");
        jpBoard.add(space12, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space17 = new JButton();
        space17.setText("17");
        jpBoard.add(space17, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space22 = new JButton();
        space22.setText("22");
        jpBoard.add(space22, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space27 = new JButton();
        space27.setText("27");
        jpBoard.add(space27, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space3 = new JButton();
        space3.setText("3");
        jpBoard.add(space3, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space13 = new JButton();
        space13.setText("13");
        jpBoard.add(space13, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space18 = new JButton();
        space18.setText("18");
        jpBoard.add(space18, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space23 = new JButton();
        space23.setText("23");
        jpBoard.add(space23, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space28 = new JButton();
        space28.setText("28");
        jpBoard.add(space28, new com.intellij.uiDesigner.core.GridConstraints(5, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space4 = new JButton();
        space4.setText("4");
        jpBoard.add(space4, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space5 = new JButton();
        space5.setText("5");
        jpBoard.add(space5, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space9 = new JButton();
        space9.setText("9");
        jpBoard.add(space9, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space10 = new JButton();
        space10.setText("10");
        jpBoard.add(space10, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space14 = new JButton();
        space14.setText("14");
        jpBoard.add(space14, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space15 = new JButton();
        space15.setText("15");
        jpBoard.add(space15, new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space19 = new JButton();
        space19.setText("19");
        jpBoard.add(space19, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space20 = new JButton();
        space20.setText("20");
        jpBoard.add(space20, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space24 = new JButton();
        space24.setText("24");
        jpBoard.add(space24, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space25 = new JButton();
        space25.setText("25");
        jpBoard.add(space25, new com.intellij.uiDesigner.core.GridConstraints(4, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space29 = new JButton();
        space29.setText("29");
        jpBoard.add(space29, new com.intellij.uiDesigner.core.GridConstraints(5, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space30 = new JButton();
        space30.setText("30");
        jpBoard.add(space30, new com.intellij.uiDesigner.core.GridConstraints(5, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space8 = new JButton();
        space8.setText("8");
        jpBoard.add(space8, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        space1 = new JButton();
        space1.setText("1");
        jpBoard.add(space1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jpConnection = new JPanel();
        jpConnection.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        jpMain.add(jpConnection, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 5, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(34, 73), null, 0, false));
        jpConnection.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Connection"));
        jpIpInfo = new JPanel();
        jpIpInfo.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        jpConnection.add(jpIpInfo, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpIpInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Connection Information"));
        jlIP = new JLabel();
        jlIP.setText("IP");
        jpIpInfo.add(jlIP, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfIpAddress = new JTextField();
        jpIpInfo.add(tfIpAddress, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jlPort = new JLabel();
        jlPort.setText("Port");
        jpIpInfo.add(jlPort, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jbConnect = new JButton();
        jbConnect.setAlignmentY(0.0f);
        jbConnect.setBorderPainted(true);
        jbConnect.setText("Connect");
        jpIpInfo.add(jbConnect, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfPort = new JTextField();
        jpIpInfo.add(tfPort, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfName = new JTextField();
        jpIpInfo.add(tfName, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cbPlayLocal = new JCheckBox();
        cbPlayLocal.setText("Play Local");
        jpIpInfo.add(cbPlayLocal, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Name");
        jpIpInfo.add(label1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jpConnectionStatus = new JPanel();
        jpConnectionStatus.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jpConnection.add(jpConnectionStatus, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(90, 217), null, 0, false));
        jpConnectionStatus.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Connection Status"));
        jlConnectionMessages = new JLabel();
        jlConnectionMessages.setText("Desconectado");
        jpConnectionStatus.add(jlConnectionMessages, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        jpMain.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(14, 82), null, 0, false));
        jpChatArea = new JPanel();
        jpChatArea.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        jpMain.add(jpChatArea, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpChatArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Chat Area"));
        tfMsgmToSend = new JTextField();
        jpChatArea.add(tfMsgmToSend, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(true);
        jpChatArea.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        taReceiveArea = new JTextArea();
        taReceiveArea.setAutoscrolls(false);
        taReceiveArea.setEditable(false);
        taReceiveArea.setLineWrap(false);
        taReceiveArea.setRequestFocusEnabled(true);
        taReceiveArea.setRows(5);
        scrollPane1.setViewportView(taReceiveArea);
        jbSend = new JButton();
        jbSend.setText("Send");
        jpChatArea.add(jbSend, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jlTurn = new JLabel();
        jlTurn.setText("Controle de turno");
        jpMain.add(jlTurn, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jbRestartGame = new JButton();
        jbRestartGame.setText("Restart Game");
        jpMain.add(jbRestartGame, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jbStartGame = new JButton();
        jbStartGame.setText("Start Game");
        jpMain.add(jbStartGame, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jbYield = new JButton();
        jbYield.setText("Yield");
        jpMain.add(jbYield, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jpStatus = new JPanel();
        jpStatus.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        jpMain.add(jpStatus, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpStatus.setBorder(BorderFactory.createTitledBorder("Game Status"));
        jpPieces = new JPanel();
        jpPieces.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jpStatus.add(jpPieces, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpPieces.setBorder(BorderFactory.createTitledBorder("Not Played Pieces"));
        jlHandPieces = new JLabel();
        jlHandPieces.setText("30");
        jpPieces.add(jlHandPieces, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jpCaptured = new JPanel();
        jpCaptured.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jpStatus.add(jpCaptured, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpCaptured.setBorder(BorderFactory.createTitledBorder("Captured Pieces"));
        jlCapturedPieces = new JLabel();
        jlCapturedPieces.setText("30");
        jpCaptured.add(jlCapturedPieces, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jpPlayedPieces = new JPanel();
        jpPlayedPieces.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jpStatus.add(jpPlayedPieces, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jpPlayedPieces.setBorder(BorderFactory.createTitledBorder("Played Pieces"));
        jlPlayedPieces = new JLabel();
        jlPlayedPieces.setText("30");
        jpPlayedPieces.add(jlPlayedPieces, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jbAddPiece = new JButton();
        jbAddPiece.setText("Add New Piece");
        jpStatus.add(jbAddPiece, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jpMain;
    }
}
