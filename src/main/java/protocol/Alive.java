package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


public class Alive implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public AliveBody messageBody;
    public class AliveBody extends MessageBody {

    }
    public Alive (){
        this.messageType = MessageType.alive;
        AliveBody body = new AliveBody();
        this.messageBody = body;
    }

    public AliveBody getMessageBody() {
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
