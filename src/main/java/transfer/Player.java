package transfer;

import server.CardTypes.Card;
import server.Deck.GameDeck;
import server.Deck.ProgrammingDeck;
import server.Game.RR;
import server.Player.Robot;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Player for Chatroom
 */

//ToDo: change name because we have two Player classes; this class is just for the communication in the chat

public class Player {


    public int priority = 1;
    public int energyCubes;
    private final String name;


    public Player(String name) {
        this.name = name;
        this.energyCubes=1;
    }

    public String getName() {return name;}
}
