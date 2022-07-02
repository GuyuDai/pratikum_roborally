package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ReceivedChat extends Message {

    public class ReceivedChatBody extends MessageBody {
        protected String message;
        protected int from;
        private boolean isPrivate;

        public String getMessage() {
            return message;
        }

        public int getFrom() {
            return from;
        }

        public boolean isPrivate() {
            return isPrivate;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public void setPrivate(boolean aPrivate) {
            isPrivate = aPrivate;
        }
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
