package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * Dropping all cards and pulling cards blindly: This will happen if a player was too slow in filling his register.
 * Empty registers will be filled automatically with the players cardsInHand in the given order.
 */

public class CardsYouGotNow extends Message {

    private class CardYouGotNowBody extends AbstractMessageBody{
        protected String[] cards;
    }
    public CardsYouGotNow (String[] cards){
        this.messageType = MessageType.cardsYouGotNow;
        CardYouGotNowBody body = new CardYouGotNowBody();
        body.cards = cards;
        this.messageBody = body;

    }
}
