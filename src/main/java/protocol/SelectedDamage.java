package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * SelectedDamage()
 * In case there is only one damageCard left in the availablePile but the client has to pick 2 damageCards,
 * the client gets the last damageCard and can continue playing.
 * if there are no damageCards in the available Pile anymore, the client can't pick any cards until
 * the availablePile contains a damageCard again.
 */

public class SelectedDamage extends Message {
    public SelectedDamageBody messageBody;

    public class SelectedDamageBody extends MessageBody {
        protected String[] cards;

        public String[] getCards() {
            return cards;
        }

        public void setCards(String[] cards) {
            this.cards = cards;
        }
    }
    public SelectedDamage (String[] cards){
        this.messageType = MessageType.selectedDamage;
        SelectedDamageBody body = new SelectedDamageBody();
        body.cards = cards;
        this.messageBody = body;

    }

    public SelectedDamageBody getMessageBody() {
        return messageBody;
    }
}
