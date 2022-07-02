package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * Dropping all cards and pulling cards blindly: This will happen if a player was too slow in filling his register.
 * Empty registers will be filled automatically with the players cardsInHand in the given order.
 */

public class CardsYouGotNow {

    public final String messageType = MessageType.cardsYouGotNow;
    public MessageBody messageBody;


    public CardsYouGotNow (String[] cards){
        MessageBody messageBody = new MessageBody();
        messageBody.cards = cards;
        this.messageBody = messageBody;

    }
}
