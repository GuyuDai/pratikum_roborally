package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class StartingPointTaken {

    public final String messageType = MessageType.startingPointTaken;
    public MessageBody messageBody;


    public StartingPointTaken(int x, int y, int clientID) {
        MessageBody messageBody = new MessageBody();
        messageBody.x = x;
        messageBody.y = y;
        messageBody.clientID = clientID;
        this.messageBody = messageBody;

    }
}
