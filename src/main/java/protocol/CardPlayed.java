package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class CardPlayed extends Message {

    private class CardPlayedBody extends AbstractMessageBody{
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
