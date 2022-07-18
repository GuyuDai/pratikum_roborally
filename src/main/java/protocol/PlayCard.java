package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class PlayCard extends Message {
    public PlayCardBody messageBody;

    public class PlayCardBody extends MessageBody {
        protected String card;

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }
    }
    public PlayCard (String card) {
        this.messageType = MessageType.playCard;
        PlayCardBody body = new PlayCardBody();
        body.card = card;
        this.messageBody = body;

    }

    public PlayCardBody getMessageBody() {
        return messageBody;
    }
}
