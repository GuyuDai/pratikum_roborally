package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class PlayerStatus implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public PlayerStatusBody messageBody;

    public class PlayerStatusBody extends MessageBody {
        protected int clientID;
        protected boolean ready;

        public int getClientID() {
            return clientID;
        }

        public boolean isReady() {
            return ready;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setReady(boolean ready) {
            this.ready = ready;
        }
    }
    public PlayerStatus (int clientID, boolean ready) {
        this.messageType = MessageType.playerStatus;
        PlayerStatusBody body = new PlayerStatusBody();
        body.clientID = clientID;
        body.ready = ready;
        this.messageBody = body;

    }

    public PlayerStatusBody getMessageBody() {
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

