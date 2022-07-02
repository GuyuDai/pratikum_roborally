package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

import java.util.ArrayList;


/**
 * Active programming cards
 * In each round a message announcing the current active cards will be sent to all players.
 * Only the cards of the current register will be announced.
 * The priorities of the cards have to be recalculated after each register.
 */

public class CurrentCards {

    public final String messageType = MessageType.currentCards;
    public MessageBody messageBody;


    public CurrentCards (ArrayList<ActiveCards> activeCards) {
        MessageBody messageBody = new MessageBody();
        messageBody.activeCards = activeCards;
        this.messageBody = messageBody;

    }
    public String getMessageType() {
        return messageType;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }
}
