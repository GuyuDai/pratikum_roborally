package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ReceivedChat implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public ReceivedChatBody messageBody;

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

    public ReceivedChatBody getMessageBody() {
        return messageBody;
    }

    @Override
    public String toString(){
        Gson gson = new GsonBuilder().create();
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        //Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
