package model;

/**
 * Created by ellca on 19/04/2017.
 */
public class Message {

    public static final String TYPE_CHAT = "chat";
    public static final String TYPE_GAME = "game";

    public static final String SUBTYPE_GAME_ADD = "game_add";
    public static final String SUBTYPE_GAME_REMOVE = "game_remove";
    public static final String SUBTYPE_GAME_MOVE = "game_move";
    public static final String SUBTYPE_GAME_CAPTURE = "game_capture";
    public static final String START_MATCH = "istart";
    public static final String END_TURN = "endturn";
    public static final String ASK_RESTART_GAME = "ask_retart_game";
    public static final String ACCEPT_RESTART_GAME = "yes_retart_game";
    public static final String ANOUNCE_WIN = "iwin";
    public static final String ANOUNCE_LOST= "ilost";

    public static final String SENDER_SERVER = "server";


    public static final String SERVER_TERMINATION = "exit_server" ;
    public static final String CLIENT_TERMINATION = "exit_client" ;
    public static final String NOT_ENOUGH_CLIENTS = "not_enough_clients";
    public static final String ALL_ENTERED = "all_entered";
    public static final String SERVER_LEFT = "server_left";
    public static final String FINISH_GAME = "finish_game";
    public static final String TIE = "tie";
    public static final String NOTTIE = "nottie";


    String type;
    String subtype;
    String sender;
    String message;
    String player;
    String start_move;
    String end_mode;
    String cap_pos;


    public Message(String type, String sender, String message) {
        this.type = type;
        this.sender = sender;
        this.message = message;
    }

    public Message(String type, String subtype, String sender, String message, String player) {
        this.type = type;
        this.subtype = subtype;
        this.sender = sender;
        this.message = message;
        this.player = player;
    }

    public Message(String type, String subtype, String sender, String message) {
        this.type = type;
        this.subtype = subtype;
        this.sender = sender;
        this.message = message;
    }

    public Message(String type, String subtype, String sender, String player, String start_move, String end_mode) {
        this.type = type;
        this.subtype = subtype;
        this.sender = sender;
        this.player = player;
        this.start_move = start_move;
        this.end_mode = end_mode;
    }

    public Message() {
    }



    public String getStart_move() {
        return start_move;
    }

    public void setStart_move(String start_move) {
        this.start_move = start_move;
    }

    public String getEnd_mode() {
        return end_mode;
    }

    public void setEnd_mode(String end_mode) {
        this.end_mode = end_mode;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isChat() {
        return type.equals(TYPE_CHAT);
    }

    public boolean isFromServer() {
        return sender.equals(SENDER_SERVER);
    }

    public boolean isStartMatch() {
        return message.equals(Message.START_MATCH);
    }

    public boolean isEndTurn() {
        return message.equals(END_TURN);
    }

    public boolean isNotNoughClients() {

        return message.equals(Message.NOT_ENOUGH_CLIENTS);
    }

    public boolean isAllEntered() {
        return  message.equals(Message.ALL_ENTERED);
    }
}
