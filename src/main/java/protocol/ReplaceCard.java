package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * Some cards have to be replaced by e.g. spam cards during the activation of the register.
 */

public class ReplaceCard extends Message {

    private class ReplaceCardBody extends MessageBody {
        protected int register;
        protected String newCard;
        protected int clientID;
    }
    public ReplaceCard (int register, String newCard, int clientID) {
        this.messageType = MessageType.replaceCard;
        ReplaceCardBody body = new ReplaceCardBody();
        body.register =register;
        body.newCard = newCard;
        body.clientID = clientID;
        this.messageBody = body;

    }
}
