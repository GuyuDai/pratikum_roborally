package protocol;

import com.google.gson.*;
import protocol.ProtocolFormat.*;

/**
 * @author Dai, Nik
 */
public class Boink implements Message{
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public BoinkBody messageBody;

    public class BoinkBody extends MessageBody{
        protected String orientation;

        public String getOrientation(){
            return orientation;
        }
    }

    public Boink (String orientation){
        this.messageType = MessageType.boink;
        Boink.BoinkBody body = new Boink.BoinkBody();
        body.orientation = orientation;
        this.messageBody = body;
    }

    public BoinkBody getMessageBody() {
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
