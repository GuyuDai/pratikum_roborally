package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * The first 9 programming cards are drawn and given to a player.
 * This message is only sent to the player receiving the programming cards.
 */

public class YourCards extends Message {

    public class YourCardsBody extends MessageBody {
        protected String[] cardsInHand;

        public String[] getCardsInHand() {
            return cardsInHand;
        }

        public void setCardsInHand(String[] cardsInHand) {
            this.cardsInHand = cardsInHand;
        }
    }
    public YourCards (String[] cardsInHand) {
        this.messageType = MessageType.yourCards;
        YourCardsBody body = new YourCardsBody();
        body.cardsInHand = cardsInHand;
        this.messageBody = body.toString();

    }

}
