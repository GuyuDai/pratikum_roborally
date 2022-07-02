package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;


public class RebootDirection {

    public final String messageType = MessageType.rebootDirection;
    public MessageBody messageBody;


    public RebootDirection (String direction){
        MessageBody messageBody = new MessageBody();
        messageBody.direction = direction;
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
