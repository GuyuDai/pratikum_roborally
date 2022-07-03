package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class CurrentPlayer extends Message {

    public class CurrentPlayerBody extends MessageBody {
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }
    }
    public CurrentPlayer (int clientID) {
        this.messageType = MessageType.currentPlayer;
        CurrentPlayerBody body = new CurrentPlayerBody();
        body.clientID = clientID;
        this.messageBody = body;

    }
}
