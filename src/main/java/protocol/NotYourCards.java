package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


public class NotYourCards extends Message {
    public NotYourCardsBody messageBody;

    public class NotYourCardsBody extends MessageBody {
        protected int clientID;
        protected int cardsInHand;

        public int getClientID() {
            return clientID;
        }

        public int getCardsInHand() {
            return cardsInHand;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setCardsInHand(int cardsInHand) {
            this.cardsInHand = cardsInHand;
        }
    }
    public NotYourCards (int clientID, int cardsInHand) {
        this.messageType = MessageType.notYourCards;
        NotYourCardsBody body = new NotYourCardsBody();
        body.clientID = clientID;
        body.cardsInHand = cardsInHand;
        this.messageBody = body;

    }

    public NotYourCardsBody getMessageBody() {
        return messageBody;
    }
}
