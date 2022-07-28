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
public class StartingPointTaken implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public StartingPointTakenBody messageBody;

    public class StartingPointTakenBody extends MessageBody {
        protected int x;
        protected int y;
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setX(int x) {
            this.x = x;
        }
    }

    /**
     * Protocoll for StartingPointTaken
     */
    public StartingPointTaken(int x, int y, int clientID) {
        this.messageType = MessageType.startingPointTaken;
        StartingPointTakenBody body = new StartingPointTakenBody();
        body.x = x;
        body.y = y;
        body.clientID = clientID;
        this.messageBody = body;

    }

    /**
     * MessageBody for StartingPointTaken
     */
    public StartingPointTakenBody getMessageBody() {
        return messageBody;
    }

    /**
     * Json
     */
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
