package model;

/**
 * Created by ellca on 19/04/2017.
 */
public class Message {

    public static String TYPE_CHAT = "chat";
    public static String TYPE_GAME = "game";
    public static String SENDER_SERVER = "server";
    public static String START_MATCH = "istart";

    String type;
    String sender;
    String message;

    public Message(String type, String sender, String message) {
        this.type = type;
        this.sender = sender;
        this.message = message;
    }

    public Message() {
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
}
