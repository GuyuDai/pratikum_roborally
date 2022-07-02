package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class SetStartingPoint{

    public final String messageType = MessageType.setStartingPoint;
    public MessageBody messageBody;


    public SetStartingPoint (int x, int y) {
        MessageBody messageBody = new MessageBody();
        messageBody.x=x;
        messageBody.y=y;
        this.messageBody = messageBody;

    }
}
