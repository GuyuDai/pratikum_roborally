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
public class SetStatus implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public SetStatusBody messageBody;

    public class SetStatusBody extends MessageBody {
        protected boolean ready;

        public boolean isReady() {
            return ready;
        }

        public void setReady(boolean ready) {
            this.ready = ready;
        }
    }
    public SetStatus (boolean ready) {
        this.messageType = MessageType.setStatus;
        SetStatusBody body = new SetStatusBody();
        body.ready = ready;
        this.messageBody = body;

    }

    public SetStatusBody getMessageBody() {
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
