package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ShuffleCoding extends Message {

    public class ShuffleCodingBody extends MessageBody {
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }
    }
    public ShuffleCoding (int clientID) {
        this.messageType = MessageType.shuffleCoding;
        ShuffleCodingBody body = new ShuffleCodingBody();
        body.clientID = clientID;
        this.messageBody = body;

    }
}


