package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ActivePhase implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public ActivePhaseBody messageBody;
    public class ActivePhaseBody extends MessageBody {
        protected int phase;

        public int getPhase() {
            return phase;
        }

        public void setPhase(int phase) {
            this.phase = phase;
        }
    }
    public ActivePhase (int phase) {
        this.messageType = MessageType.activePhase;
        ActivePhaseBody body = new ActivePhaseBody();
        body.phase = phase;
        this.messageBody = body;
    }

    public ActivePhaseBody getMessageBody() {
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

