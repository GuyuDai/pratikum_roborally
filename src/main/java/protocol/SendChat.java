package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SendChat extends Message {
    public SendChatBody messageBody;

    public class SendChatBody extends MessageBody {
        protected String message;
        protected int to;

        public String getMessage() {
            return message;
        }

        public int getTo() {
            return to;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setTo(int to) {
            this.to = to;
        }
    }
    public SendChat (String message, int to) {
        this.messageType = MessageType.sendChat;
        SendChatBody body = new SendChatBody();
        body.message = message;
        body.to = to;
        this.messageBody = body;

    }

    public SendChatBody getMessageBody() {
        return messageBody;
    }
}
