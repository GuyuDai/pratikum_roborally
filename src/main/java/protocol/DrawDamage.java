package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * The client receives all his damage cards in one message at once.
 */

public class DrawDamage extends Message {

    private class DrawDamageBody extends AbstractMessageBody{
        protected int clientID;
        protected String[] cards;
    }
    public DrawDamage (int clientID, String[] cards) {
        this.messageType = MessageType.drawDamage;
        DrawDamageBody body = new DrawDamageBody();
        body.clientID = clientID;
        body.cards = cards;
        this.messageBody = body;

    }
}
