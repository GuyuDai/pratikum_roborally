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
public class HelloServer implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public HelloServerBody messageBody;

    public class HelloServerBody extends MessageBody {
        protected String group;
        protected boolean isAI;
        protected String protocol;
        protected int clientID;

        public String getProtocol() {
            return protocol;
        }

        public String getGroup() {
            return group;
        }

        public int getClientID() {
            return clientID;
        }

        public boolean isAI() {
            return isAI;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setAI(boolean AI) {
            isAI = AI;
        }
    }

    public HelloServer(String group, boolean isAi, String protocol, int clientID) {
        this.messageType = MessageType.helloServer;
        HelloServerBody body = new HelloServerBody();
        body.group = group;
        body.isAI = isAi;
        body.protocol = protocol;
        body.clientID = clientID;

        this.messageBody = body;
        //this.messageBody = body.toString();

    }

    public HelloServerBody getMessageBody() {
        return messageBody;
    }

    /*
    @Override
    public String toString(){
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(this.getClass(), new HelloServerAdapter());
        gsonBuilder.setPrettyPrinting();
        final Gson gson = gsonBuilder.create();

        return gson.toJson(this);
    }

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
