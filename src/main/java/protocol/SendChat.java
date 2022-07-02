package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SendChat extends Message {

    private class SendChatBody extends MessageBody {
        protected String message;
        protected int to;
    }
    public SendChat (String message, int to) {
        this.messageType = MessageType.sendChat;
        SendChatBody body = new SendChatBody();
        body.message = message;
        body.to = to;
        this.messageBody = body;

    }
}
