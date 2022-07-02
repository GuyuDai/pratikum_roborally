package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;


public class HelloServer {


    public final String messageType = MessageType.helloServer;
    public MessageBody messageBody;

    public HelloServer(String group, boolean isAi, String protocol, int clientID) {
        MessageBody messageBody = new MessageBody();
        messageBody.group = group;
        messageBody.isAI = isAi;
        messageBody.protocol = protocol;
        messageBody.clientID = clientID;

        this.messageBody = messageBody;

    }
}
