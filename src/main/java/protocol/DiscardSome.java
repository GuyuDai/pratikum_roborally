package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.*;

/**
 * @author Nik
 */
public class DiscardSome extends Message {
    public DiscardSomeBody messageBody;

    public class DiscardSomeBody extends MessageBody{
        protected String [] cards;
        public String[] getCards() {
            return cards;
        }

    }
    public DiscardSome(String[] cards){
        this.messageType = MessageType.discardSome;
        DiscardSome.DiscardSomeBody body = new DiscardSome.DiscardSomeBody();
        body.cards = cards;
        this.messageBody = body;
    }

    public DiscardSomeBody getMessageBody() {
        return messageBody;
    }
}
