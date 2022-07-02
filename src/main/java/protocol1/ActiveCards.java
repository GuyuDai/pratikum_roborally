package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;


public class ActiveCards {

    public final String messageType = MessageType.activeCards;
    public MessageBody messageBody;


    public ActiveCards (int clientID, String card) {
            MessageBody messageBody = new MessageBody();
            messageBody.clientID =clientID;
            messageBody.card = card;
            this.messageBody = messageBody;

        }
}
