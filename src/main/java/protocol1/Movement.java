package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * The Movements of a client will be broadcasted to all players
 * Only movements between fields are performed. There are no changes of the Orientation.
 */

public class Movement {

    public final String messageType = MessageType.movement;
    public MessageBody messageBody;

    public Movement (int clientID, int x, int y) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        messageBody.x = x;
        messageBody.y = y;
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
