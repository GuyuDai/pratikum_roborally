package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class ShuffleCoding {

    public final String messageType = MessageType.shuffleCoding;
    public MessageBody messageBody;


    public ShuffleCoding (int clientID) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        this.messageBody = messageBody;

    }
}


