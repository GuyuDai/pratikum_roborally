package transfer;

/**
 * Player for Chatroom
 */

//ToDo: change name because we have two Player classes; this class is just for the communication in the chat

public class Player {

    private final String name;


    public Player(String name) {
        this.name = name;
    }

    public String getName() {return name;}
}
