package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class Welcome extends Message {

    private class WelcomeBody extends AbstractMessageBody{
        protected int clientID;
    }
    public Welcome (int clientID) {
        this.messageType = MessageType.welcome;
        WelcomeBody body = new WelcomeBody();
        body.clientID = clientID;
        this.messageBody = body;

    }
}
