package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class SendChat {

    public final String messageType = MessageType.sendChat;
    public MessageBody messageBody;


    public SendChat (String message, int to) {
        MessageBody messageBody = new MessageBody();
        messageBody.message = message;
        messageBody.to = to;
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
