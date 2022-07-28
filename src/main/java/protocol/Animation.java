package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * @author Dai
 * For Animation of BlueConveyorBelt, GreenConveyorBelt, PushPanel, Gear, CheckPoint,
 * PlayerShooting, WallShooting,EnergySpace. Not necessary.
 */

public class Animation implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public AnimationBody messageBody;

    public class AnimationBody extends MessageBody {
        protected String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    public Animation (String type){
        this.messageType = MessageType.animation;
        AnimationBody body = new AnimationBody();
        body.type = type;
        this.messageBody = body;

    }

    public AnimationBody getMessageBody() {
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
