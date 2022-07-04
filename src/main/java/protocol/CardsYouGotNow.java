package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * Dropping all cards and pulling cards blindly: This will happen if a player was too slow in filling his register.
 * Empty registers will be filled automatically with the players cardsInHand in the given order.
 */

public class CardsYouGotNow extends Message {

    public class CardYouGotNowBody extends MessageBody {
        protected String[] cards;

        public String[] getCards() {
            return cards;
        }

        public void setCards(String[] cards) {
            this.cards = cards;
        }
    }
    public CardsYouGotNow (String[] cards){
        this.messageType = MessageType.cardsYouGotNow;
        CardYouGotNowBody body = new CardYouGotNowBody();
        body.cards = cards;
        this.messageBody = body.toString();

    }
}
