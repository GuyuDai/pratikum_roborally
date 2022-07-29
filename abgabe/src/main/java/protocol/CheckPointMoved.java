package protocol;

import com.google.gson.*;
import protocol.ProtocolFormat.*;

/**
 * @author Niklas Nissl, Dai, Djafari
 * The server sends the new position of the according checkpoint that has moved
 */
public class CheckPointMoved implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public CheckPointMovedBody messageBody;

    public class CheckPointMovedBody extends MessageBody{
        protected int checkPointID;
        protected int x;
        protected int y;

        public int getCheckPointID() {
            return checkPointID;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }

    }

    public CheckPointMoved (int checkPointID, int x, int y){
        this.messageType = MessageType.checkPointMoved;
        CheckPointMoved.CheckPointMovedBody body = new CheckPointMoved.CheckPointMovedBody();
        body.checkPointID = checkPointID;
        body.x = x;
        body.y = y;
        this.messageBody = body;
    }

    public CheckPointMovedBody getMessageBody() {
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
