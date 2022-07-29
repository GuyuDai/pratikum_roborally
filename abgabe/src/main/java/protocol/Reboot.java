package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * @author Djafari,Dai
 * Reboot(): right after the register.
 * Robot start with orientation to top/north/up
 * The coordinates of the field where the robot reboots will be sent to all players via "Movement"-message.
 */

public class Reboot implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public RebootBody messageBody;

    public class RebootBody extends MessageBody {
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }
    }

    public Reboot (int clientID){
        this.messageType = MessageType.reboot;
        RebootBody body = new RebootBody();
        body.clientID = clientID;
        this.messageBody = body;

    }

    public RebootBody getMessageBody() {
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
