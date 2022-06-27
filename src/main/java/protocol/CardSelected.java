package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * The server sends the filled registers without any information about the cards to all players.
 * "Filled" is saying whether a card was put to/ removed from the register.
 */

public class CardSelected extends Message {

    private class CardSelectedBody extends AbstractMessageBody{
        protected int clientID;
        protected int register;
        protected boolean filled;
    }

    public CardSelected (int clientID, int register, boolean filled) {
        this.messageType = MessageType.cardSelected;
        CardSelectedBody body = new CardSelectedBody();
        body.clientID = clientID;
        body.register = register;
        body.filled = filled;
        this.messageBody = body;

    }
}
