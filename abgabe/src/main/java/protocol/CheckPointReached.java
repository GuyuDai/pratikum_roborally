package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * @author Dai, Djafari, Nissl
 */
public class CheckPointReached implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public CheckPointReachedBody messageBody;

    public class CheckPointReachedBody extends MessageBody {
        protected int clientID;
        protected int number;

        public int getClientID() {
            return clientID;
        }

        public int getNumber() {
            return number;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
    public CheckPointReached (int clientID, int number){
        this.messageType = MessageType.checkpointReached;
        CheckPointReachedBody body = new CheckPointReachedBody();
        body.clientID = clientID;
        body.number = number;
        this.messageBody = body;

    }

    public CheckPointReachedBody getMessageBody() {
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
