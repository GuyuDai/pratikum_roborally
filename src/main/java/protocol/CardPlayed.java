package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class CardPlayed extends Message {

    private class CardPlayedBody extends MessageBody {
        protected int clientID;
        protected String card;
    }
    public CardPlayed (int clientID, String card) {
        this.messageType = MessageType.cardPlayed;
        CardPlayedBody body = new CardPlayedBody();
        body.clientID = clientID;
        body.card = card;
        this.messageBody = body;
    }
}
