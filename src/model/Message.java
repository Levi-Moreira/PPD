package model;

/**
 * Created by ellca on 19/04/2017.
 */
public class Message {

    public static final String TYPE_CHAT = "chat";
    public static final String TYPE_GAME = "game";
    public static final String TYPE_GAME_ADD = "game_add";
    public static final String TYPE_GAME_MOVE = "game_move";
    public static final String SENDER_SERVER = "server";
    public static final String START_MATCH = "istart";
    public static final String END_TURN = "endturn";

    String type;
    String subtype;
    String sender;
    String message;
    String player;


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

    public Message() {
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

}
