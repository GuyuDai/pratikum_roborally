package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class PlayCard {


    public final String messageType = MessageType.playCard;
    public MessageBody messageBody;



    public PlayCard (String card) {
        MessageBody messageBody = new MessageBody();
        messageBody.card = card;
        this.messageBody = messageBody;

    }
}
