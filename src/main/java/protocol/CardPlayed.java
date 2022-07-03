package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class CardPlayed extends Message {

    public class CardPlayedBody extends MessageBody {
        protected int clientID;
        protected String card;

        public int getClientID() {
            return clientID;
        }

        public String getCard() {
            return card;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setCard(String card) {
            this.card = card;
        }
    }
    public CardPlayed (int clientID, String card) {
        this.messageType = MessageType.cardPlayed;
        CardPlayedBody body = new CardPlayedBody();
        body.clientID = clientID;
        body.card = card;
        this.messageBody = body;
    }
}
