package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * @author Dai
 * The Movements of a client will be broadcasted to all players
 * Only movements between fields are performed. There are no changes of the Orientation.
 */

public class Movement implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public MovementBody messageBody;

    public class MovementBody extends MessageBody {
        protected int clientID;
        protected int x;
        protected int y;

        public int getClientID() {
            return clientID;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
    public Movement (int clientID, int x, int y) {
        this.messageType = MessageType.movement;
        MovementBody body = new MovementBody();
        body.clientID = clientID;
        body.x = x;
        body.y = y;
        this.messageBody = body;

    }

    public MovementBody getMessageBody() {
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
