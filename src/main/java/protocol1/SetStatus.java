package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class SetStatus {


    public final String messageType = MessageType.setStatus;
    public MessageBody messageBody;

    public SetStatus (boolean ready) {
        MessageBody messageBody = new MessageBody();
        messageBody.ready = ready;
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
