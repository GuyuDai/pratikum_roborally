package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class Welcome implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public WelcomeBody messageBody;

    public class WelcomeBody extends MessageBody {
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }
    }

    /**
     * Protocoll for Welcome
     */
    public Welcome (int clientID) {
        this.messageType = MessageType.welcome;
        WelcomeBody body = new WelcomeBody();
        body.clientID = clientID;
        this.messageBody = body;

    }

    /**
     * MessageBody for Welcome
     */
    public WelcomeBody getMessageBody() {
        return messageBody;
    }


    /**
     * Json
     */
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
