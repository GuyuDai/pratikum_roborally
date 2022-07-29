package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * @author Djafari, Dai
 * The direction of a turning is described as "clockwise" and "counterclockwise".
 */

public class PlayerTurning implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public PlayerTurningBody messageBody;

    public class PlayerTurningBody extends MessageBody {
        protected int clientID;
        protected String rotation;

        public int getClientID() {
            return clientID;
        }

        public String getRotation() {
            return rotation;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setRotation(String rotation) {
            this.rotation = rotation;
        }
    }
    public PlayerTurning (int clientID, String rotation){
        this.messageType = MessageType.playerTurning;
        PlayerTurningBody body = new PlayerTurningBody();
        body.clientID  = clientID;
        body.rotation = rotation;
        this.messageBody = body;

    }

    public PlayerTurningBody getMessageBody() {
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
