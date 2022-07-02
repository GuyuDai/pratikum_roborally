package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * Some cards have to be replaced by e.g. spam cards during the activation of the register.
 */

public class ReplaceCard {

    public final String messageType = MessageType.replaceCard;
    public MessageBody messageBody;

    public ReplaceCard (int register, String newCard, int clientID) {
        MessageBody messageBody = new MessageBody();
        messageBody.register =register;
        messageBody.newCard = newCard;
        messageBody.clientID = clientID;
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
