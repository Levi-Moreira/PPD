package model;

/**
 * Created by ellca on 19/04/2017.
 */
public class Message {

    String type;
    String sender;
    String message;

    public Message(String type, String sender, String message) {
        this.type = type;
        this.sender = sender;
        this.message = message;
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
}
