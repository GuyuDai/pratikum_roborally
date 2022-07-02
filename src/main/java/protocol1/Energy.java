package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class Energy {

    public final String messageType = MessageType.energy;
    public MessageBody messageBody;


    public Energy (int clientID, int count, String source){
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        messageBody.count = count;
        messageBody.source = source;
        this.messageBody = messageBody;

    }
}
