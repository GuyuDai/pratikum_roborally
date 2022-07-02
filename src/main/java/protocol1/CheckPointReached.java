package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class CheckPointReached {


    public final String messageType = MessageType.checkpointReached;
    public MessageBody messageBody;


    public CheckPointReached (int clientID, int number){
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        messageBody.number = number;
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