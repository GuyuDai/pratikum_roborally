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
public class PlayerValues implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public PlayerValuesBody messageBody;

    public class PlayerValuesBody extends MessageBody {
        protected String name;
        protected int figure;

        public int getFigure() {
            return figure;
        }

        public String getName() {
            return name;
        }

        public void setFigure(int figure) {
            this.figure = figure;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public  PlayerValues (String name, int figure) {
        this.messageType = MessageType.playerValues;
        PlayerValuesBody body = new PlayerValuesBody();
        body.name = name;
        body.figure = figure;
        this.messageBody = body;

    }

    public PlayerValuesBody getMessageBody() {
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
