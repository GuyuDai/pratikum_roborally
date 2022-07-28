package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * @author Dai, Djafari
 */
public class ConnectionUpdate implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public ConnectionUpdateBody messageBody;

    public class ConnectionUpdateBody extends MessageBody {
        protected int clientID;
        protected boolean isConnected;
        protected String action;

        public int getClientID() {
            return clientID;
        }

        public String getAction() {
            return action;
        }

        public boolean isConnected() {
            return isConnected;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public void setConnected(boolean connected) {
            isConnected = connected;
        }
    }
    public ConnectionUpdate (int clientID, boolean isConnected, String action) {
        this.messageType = MessageType.connectionUpdate;
        ConnectionUpdateBody body = new ConnectionUpdateBody();
        body.clientID = clientID;
        body.isConnected = isConnected;
        body.action = action;
        this.messageBody = body;

    }

    public ConnectionUpdateBody getMessageBody() {
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
