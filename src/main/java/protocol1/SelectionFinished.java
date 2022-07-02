package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * As soon as the player selects his 5th card, no more adjustments can be made.
 * This will be sent to all players.
 */

public class SelectionFinished {

    public final String messageType = MessageType.selectionFinished;
    public MessageBody messageBody;


    public SelectionFinished (int clientID) {
        MessageBody messageBody = new MessageBody();
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
