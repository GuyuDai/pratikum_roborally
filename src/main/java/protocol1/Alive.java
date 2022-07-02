package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;


public class Alive {

    public final String messageType = MessageType.alive;
    public MessageBody messageBody;


    public Alive (){
        MessageBody messagebody = new MessageBody();
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
