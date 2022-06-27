package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SendChat extends Message {

    private class SendChatBody extends AbstractMessageBody{
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
