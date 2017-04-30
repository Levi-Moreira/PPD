package model.entities;

/**
 * Class that will be parsed into json string to enable communication
 */
public class Message {

    //to main types of messages, chat and game
    public static final String TYPE_CHAT = "chat";
    public static final String TYPE_GAME = "game";

    //when the server sends a message, it uses this type
    public static final String SENDER_SERVER = "server";

    //game messages for subtype
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

    public static final String CLIENT_TERMINATION = "exit_client" ;
    public static final String NOT_ENOUGH_CLIENTS = "not_enough_clients";
    public static final String ALL_ENTERED = "all_entered";
    public static final String SERVER_LEFT = "server_left";
    public static final String FINISH_GAME = "finish_game";
    public static final String TIE = "tie";
    public static final String NOTTIE = "nottie";
    public static final String FULL_ROOM = "full_room" ;


    //the fields in the json message
    String type;
    String subtype;
    String sender;
    String message;
    String player;
    String start_move;
    String end_mode;


    //some useful constructors
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



    //some useful getters
    public String getStart_move() {
        return start_move;
    }

    public String getEnd_mode() {
        return end_mode;
    }

    public String getPlayer() {
        return player;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }


    /**
     * Checks if message is of type chat
     * @return true if so
     */
    public boolean isChat() {
        return type.equals(TYPE_CHAT);
    }


    /**
     * Cheks if message came from the server
     * @return true if so
     */
    public boolean isFromServer() {
        return sender.equals(SENDER_SERVER);
    }

}
