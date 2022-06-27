package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class PlayCard extends Message {

    private class PlayCardBody extends AbstractMessageBody{
        protected String card;
    }
    public PlayCard (String card) {
        this.messageType = MessageType.playCard;
        PlayCardBody body = new PlayCardBody();
        body.card = card;
        this.messageBody = body;

    }
}
