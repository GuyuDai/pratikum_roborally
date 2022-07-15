package protocol;

import protocol.ProtocolFormat.*;

/**
 * @author Nik
 */
public class DiscardSome extends Message {
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
        this.messageBody = body.toString();
    }
}
