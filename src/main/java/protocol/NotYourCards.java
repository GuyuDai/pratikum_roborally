package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


public class NotYourCards extends Message {

    public class NotYourCardsBody extends MessageBody {
        protected int clientID;
        protected String[] cardsInHand;

        public int getClientID() {
            return clientID;
        }

        public int getCardsInHand() {
            return cardsInHand;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setCardsInHand(String[] cardsInHand) {
            this.cardsInHand = cardsInHand;
        }
    }
    public NotYourCards (int clientID, String[] cardsInHand) {
        this.messageType = MessageType.notYourCards;
        NotYourCardsBody body = new NotYourCardsBody();
        body.clientID = clientID;
        body.cardsInHand = cardsInHand;
        this.messageBody = body.toString();

    }
}
