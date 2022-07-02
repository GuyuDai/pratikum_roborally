package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * The client receives all his damage cards in one message at once.
 */

public class DrawDamage {

    public final String messageType = MessageType.drawDamage;
    public MessageBody messageBody;


    public DrawDamage (int clientID, String[] cards) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        messageBody.cards = cards;
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
