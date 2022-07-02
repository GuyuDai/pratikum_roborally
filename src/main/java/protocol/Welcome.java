package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class Welcome extends Message {

    private class WelcomeBody extends MessageBody {
        protected int clientID;
    }
    public Welcome (int clientID) {
        this.messageType = MessageType.welcome;
        WelcomeBody body = new WelcomeBody();
        body.clientID = clientID;
        this.messageBody = body;

    }
}
