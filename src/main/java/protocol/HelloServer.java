package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


public class HelloServer extends Message {

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
}
