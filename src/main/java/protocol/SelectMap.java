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
public class SelectMap implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public SelectMapBody messageBody;

    public class SelectMapBody extends MessageBody {
        protected String[] availableMaps;

        public String[] getAvailableMaps() {
            return availableMaps;
        }

        public void setAvailableMaps(String[] availableMaps) {
            this.availableMaps = availableMaps;
        }
    }

    public SelectMap(String[] availableMaps) {
        this.messageType = MessageType.selectMap;
        SelectMapBody body = new SelectMapBody();
        body.availableMaps = availableMaps ;
        this.messageBody = body;

    }

    public SelectMapBody getMessageBody() {
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
