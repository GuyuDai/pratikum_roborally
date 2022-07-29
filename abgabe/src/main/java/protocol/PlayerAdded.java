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
public class PlayerAdded implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public PlayerAddedBody messageBody;

    public class PlayerAddedBody extends MessageBody {
        protected int clientID;
        protected String name;
        protected int figure;

        public int getClientID() {
            return clientID;
        }

        public String getName() {
            return name;
        }

        public int getFigure() {
            return figure;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setFigure(int figure) {
            this.figure = figure;
        }
    }
    public PlayerAdded (int clientID, String name, int figure) {
        this.messageType = MessageType.playerAdded;
        PlayerAddedBody body = new PlayerAddedBody();
        body.clientID = clientID ;
        body.name = name;
        body.figure = figure;
        this.messageBody = body;

    }

    public PlayerAddedBody getMessageBody() {
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
