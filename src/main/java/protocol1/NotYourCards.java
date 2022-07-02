package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;
import server.CardTypes.Card;

import java.util.ArrayList;

public class NotYourCards {

    public final String messageType = MessageType.notYourCards;
    public MessageBody messageBody;

    public NotYourCards (int clientID, String[] cardsInHand) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        messageBody.cardsInHand = cardsInHand;
        this.messageBody = messageBody;

    }
}
