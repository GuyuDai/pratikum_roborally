package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * The client receives all his damage cards in one message at once.
 */

public class DrawDamage extends Message {

    public class DrawDamageBody extends MessageBody {
        protected int clientID;
        protected String[] cards;

        public int getClientID() {
            return clientID;
        }

        public String[] getCards() {
            return cards;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setCards(String[] cards) {
            this.cards = cards;
        }
    }
    public DrawDamage (int clientID, String[] cards) {
        this.messageType = MessageType.drawDamage;
        DrawDamageBody body = new DrawDamageBody();
        body.clientID = clientID;
        body.cards = cards;
        this.messageBody = body;

    }
}
