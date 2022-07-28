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
public class RebootDirection implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public RebootDirectionBody messageBody;

    public class RebootDirectionBody extends MessageBody {
        protected String direction;

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
    }
    public RebootDirection (String direction){
        this.messageType = MessageType.rebootDirection;
        RebootDirectionBody body = new RebootDirectionBody();
        body.direction = direction;
        this.messageBody = body;

    }

    public RebootDirectionBody getMessageBody() {
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
