package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


public class NotYourCards extends Message {

    private class NotYourCardsBody extends MessageBody {
        protected int clientID;
        protected int cardsInHand;
    }
    public NotYourCards (int clientID, int cardsInHand) {
        this.messageType = MessageType.notYourCards;
        NotYourCardsBody body = new NotYourCardsBody();
        body.clientID = clientID;
        body.cardsInHand = cardsInHand;
        this.messageBody = body;

    }
}
