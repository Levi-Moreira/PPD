package model.io;

import model.entities.Message;
import network.Client;
import network.IClient;
import network.IServer;
import presenter.ClientPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Intefaces the communication between the lower level client and the upper lever clientPresenter
 */
public class ClientNetworkModel {

    //the lower level client
    private Client client;

    //the upper level clientPresenter
    private ClientPresenter clientPresenter;

    private String clientName;

    //constructor
    public ClientNetworkModel(ClientPresenter clientPresenter) {
        this.clientPresenter = clientPresenter;
    }


    /**
     * Start up the client for a remote communication
     *
     * @param clientName the name of the client
     * @param ip         the ip address of the server
     * @param port       the port in the server where the app lives
     */
    public void startUpClient(String clientName, String ip, int port, boolean remote) throws MalformedURLException {
        this.clientName = clientName;

        if (!remote) {
            String serverURL = "rmi://localhost/IServer";
            IServer server = null;
            try {
                server = (IServer) Naming.lookup(serverURL);
            } catch (NotBoundException e) {
                e.printStackTrace();
                showConnectionError();
            } catch (RemoteException e) {
                e.printStackTrace();
                showConnectionError();
            }
            try {
                client = new Client(server, this);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            client.connect();
        }
    }

    /**
     * Anounces to the preseneter that the client is connected
     */
    public void clientConnected() {
        clientPresenter.clientConnected();
    }


    /**
     * Encapsulates a message in a json package with a type chat
     *
     * @param text the raw msg to be sent
     */
    public void sendChatMessage(String text) {
       /* Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();
        Message msg = new Message(Message.TYPE_CHAT, clientName, text);

        String json = gson.toJson(msg, type);*/

        try {
            client.sendChatMessage(text);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * When a message was received from the network, change it to an object Message
     *
     * @param mRcv the received json
     */
    public void receivedMessage(String mRcv) {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = gson.fromJson(mRcv, Message.class);

        //ask the clientPresenter to treat the message
        if (msg != null) ;
        //clientPresenter.receivedMessage(msg);
    }


    /**
     * Anounces to the clientPresenter that there was a connection error
     */
    public void showConnectionError() {
        clientPresenter.showConnectionError();
    }

    /**
     * Create a start game message
     *
     * @param pieceColour the colour of the piece chosen by the starting player
     */
    public void requestStartMatch(int pieceColour) {
        try {
            client.requestStartMatch(pieceColour);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create an end turn message
     */
    public void endMyTurn() {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();
        Message msg = new Message(Message.TYPE_GAME, Message.END_TURN, clientName, "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates an add to space message
     *
     * @param space        the space to which the piece needs to be added
     * @param playerNumber the player number to add to te space
     */
    public void addToSpace(int space, int playerNumber) {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.SUBTYPE_GAME_ADD, clientName, space + "", playerNumber + "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a move message
     *
     * @param start        start position of the move
     * @param end          end position of the move
     * @param playerNumber playerNumber associated with the move
     */
    public void move(int start, int end, int playerNumber) {

        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.SUBTYPE_GAME_MOVE, clientName, playerNumber + "", start + "", end + "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a terminate client message
     */
    public void terminateClient() {

        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.CLIENT_TERMINATION, clientName, "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        client.terminane();
    }

    /**
     * Generates a ask for restart message, the end user will be asked to restart the game
     */
    public void askForRestart() {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.ASK_RESTART_GAME, clientName, "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a message accepting the restart of the game
     */
    public void sendAcceptRestartMessage() {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.ACCEPT_RESTART_GAME, clientName, "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Generates a capture message
     *
     * @param capturedPos the position where the capture happened
     */
    public void performCapture(int capturedPos) {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.SUBTYPE_GAME_CAPTURE, clientName, capturedPos + "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a removal message
     *
     * @param removePos the position where the removal happened
     */
    public void performRemoval(int removePos) {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.SUBTYPE_GAME_REMOVE, clientName, removePos + "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates an anounce win message
     */
    public void anounceWin() {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.ANOUNCE_WIN, clientName, "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * Closes this client
     */
    public void closeClient() {
        client.close();
    }

    /**
     * Generates a message to anounce the ending of a game
     *
     * @param piecesOnBoard the number of pieces this user have on his board
     */
    public void finishGame(int piecesOnBoard) {

        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.FINISH_GAME, clientName, piecesOnBoard + "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a message to announce that a tie happened
     */
    public void anounceTie() {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.TIE, clientName, "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a message to announce that there was no tie
     */
    public void anounceNotTie() {
        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.NOTTIE, clientName, "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a message to anounce that his player lost
     */
    public void anounceLost() {

        Gson gson = new Gson();
        Type type = new TypeToken<Message>() {
        }.getType();

        Message msg = new Message(Message.TYPE_GAME, Message.ANOUNCE_LOST, clientName, "");

        String json = gson.toJson(msg, type);

        try {
            client.sendChatMessage(json);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void recieveChatMessage(IClient sender, String message) {

        try {
            clientPresenter.receivedChatMessage(sender, message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return clientName;
    }

    public void startGame(int pieceColour) {
        clientPresenter.startGame(pieceColour);
    }

    public void signalNotEnoughClients() {
        clientPresenter.signalNotEnoughClients();
    }

    public void signalAllEntered() {
        clientPresenter.signalAllEntered();
    }

    public void signalFullRoom() {
        clientPresenter.signalFullRoom();
    }
}
