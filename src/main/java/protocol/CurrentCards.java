package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.ActiveCard;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

import java.util.List;


/**
 * Active programming cards
 * In each round a message announcing the current active cards will be sent to all players.
 * Only the cards of the current register will be announced.
 * The priorities of the cards have to be recalculated after each register.
 */

public class CurrentCards extends Message {

    private class CurrentCardsBody extends MessageBody {
        protected List<ActiveCard> activeCards;
    }
    public CurrentCards (List<ActiveCard> activeCards) {
        this.messageType = MessageType.currentCards;
        CurrentCardsBody body = new CurrentCardsBody();
        body.activeCards = activeCards;
        this.messageBody = body;

    }
}
