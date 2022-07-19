package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class MapSelected implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public MapSelectedBody messageBody;

    public class MapSelectedBody extends MessageBody {
        protected String map;

        public String getMap() {
            return map;
        }

        public void setMap(String map) {
            this.map = map;
        }
    }
    public MapSelected (String map) {
        this.messageType = MessageType.mapSelected;
        MapSelectedBody body = new MapSelectedBody();
        body.map = map;
        this.messageBody = body;

    }

    public MapSelectedBody getMessageBody() {
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
