package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


public class HelloServer extends Message {

    private class HelloServerBody extends MessageBody {
        protected String group;
        protected boolean isAI;
        protected String protocol;
        protected int clientID;

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
