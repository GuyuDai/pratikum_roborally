package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * The first 9 programming cards are drawn and given to a player.
 * This message is only sent to the player receiving the programming cards.
 */

public class YourCards {

    public final String messageType = MessageType.yourCards;
    public MessageBody messageBody;


    public YourCards (String[] cardsInHand) {
        MessageBody messageBody = new MessageBody();
        messageBody.cardsInHand = cardsInHand;
        this.messageBody = messageBody;

    }

}
