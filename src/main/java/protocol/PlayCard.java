package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class PlayCard extends Message {

    private class PlayCardBody extends MessageBody {
        protected String card;
    }
    public PlayCard (String card) {
        this.messageType = MessageType.playCard;
        PlayCardBody body = new PlayCardBody();
        body.card = card;
        this.messageBody = body;

    }
}
