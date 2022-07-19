package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ShuffleCoding implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public ShuffleCodingBody messageBody;

    public class ShuffleCodingBody extends MessageBody {
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }
    }
    public ShuffleCoding (int clientID) {
        this.messageType = MessageType.shuffleCoding;
        ShuffleCodingBody body = new ShuffleCodingBody();
        body.clientID = clientID;
        this.messageBody = body;

    }

    public ShuffleCodingBody getMessageBody() {
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


