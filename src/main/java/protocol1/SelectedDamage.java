package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * SelectedDamage()
 * In case there is only one damageCard left in the availablePile but the client has to pick 2 damageCards,
 * the client gets the last damageCard and can continue playing.
 * if there are no damageCards in the available Pile anymore, the client can't pick any cards until
 * the availablePile contains a damageCard again.
 */

public class SelectedDamage {

    public final String messageType = MessageType.selectedDamage;
    public MessageBody messageBody;


    public SelectedDamage (String[] cards){
        MessageBody messageBody = new MessageBody();
        messageBody.cards = cards;
        this.messageBody = messageBody;

    }
    public String getMessageType() {
        return messageType;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }
}
