package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


/**
 * @author Djafari, Dai
 */
public class SendChat implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
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
