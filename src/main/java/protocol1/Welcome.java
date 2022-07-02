package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class Welcome {

    public final String messageType = MessageType.welcome;
    public MessageBody messageBody;


    public Welcome (int clientID) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
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
