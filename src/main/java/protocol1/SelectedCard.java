package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * The Cards selected by the players will be sent one by one to the server.
 * The player can override/ remove a card from his register.
 * This is done by setting the overwritten/ removed card to NULL.
 */

public class SelectedCard {

    public final String messageType = MessageType.selectCard;
    public MessageBody messageBody;


    public SelectedCard (String card, int register) {
        MessageBody messageBody = new MessageBody();
        messageBody.card = card;
        messageBody.register = register;
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
