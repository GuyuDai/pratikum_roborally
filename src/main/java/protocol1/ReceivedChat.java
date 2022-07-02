package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class ReceivedChat {

    public final String messageType = MessageType.receivedChat;
    public MessageBody messageBody;

    public ReceivedChat (String message, int from, boolean isPrivate) {
        MessageBody messageBody = new MessageBody();
        messageBody.message = message;
        messageBody.from = from;
        messageBody.isPrivate = isPrivate;
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
