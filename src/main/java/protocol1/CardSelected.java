package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * The server sends the filled registers without any information about the cards to all players.
 * "Filled" is saying whether a card was put to/ removed from the register.
 */

public class CardSelected {

    public final String messageType = MessageType.cardSelected;
    public MessageBody messageBody;


    public CardSelected (int clientID, int register, boolean filled) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        messageBody.register = register;
        messageBody.filled = filled;
        this.messageBody = messageBody;

    }
}
