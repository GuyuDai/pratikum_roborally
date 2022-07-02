package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ReceivedChat extends Message {

    private class ReceivedChatBody extends MessageBody {
        protected String message;
        protected int from;
        private boolean isPrivate;
    }
    public ReceivedChat (String message, int from, boolean isPrivate) {
        this.messageType = MessageType.receivedChat;
        ReceivedChatBody body = new ReceivedChatBody();
        body.message = message;
        body.from = from;
        body.isPrivate = isPrivate;
        this.messageBody = body;

    }
}
