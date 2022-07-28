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
public class GameFinished implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public GameFinishedBody messageBody;

    public class GameFinishedBody extends MessageBody {
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }
    }
    public GameFinished (int clientID){
        this.messageType = MessageType.gameFinished;
        GameFinishedBody body = new GameFinishedBody();
        body.clientID = clientID;
        this.messageBody = body;

    }

    public GameFinishedBody getMessageBody() {
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
