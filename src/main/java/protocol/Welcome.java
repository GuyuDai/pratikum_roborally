package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class Welcome extends Message {
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
    public Welcome (int clientID) {
        this.messageType = MessageType.welcome;
        WelcomeBody body = new WelcomeBody();
        body.clientID = clientID;
        this.messageBody = body;

    }

    public WelcomeBody getMessageBody() {
        return messageBody;
    }
}
