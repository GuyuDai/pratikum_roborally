package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class PlayerStatus extends Message {

    public class PlayerStatusBody extends MessageBody {
        protected int clientID;
        protected boolean ready;

        public int getClientID() {
            return clientID;
        }

        public boolean isReady() {
            return ready;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setReady(boolean ready) {
            this.ready = ready;
        }
    }
    public PlayerStatus (int clientID, boolean ready) {
        this.messageType = MessageType.playerStatus;
        PlayerStatusBody body = new PlayerStatusBody();
        body.clientID = clientID;
        body.ready = ready;
        this.messageBody = body;

    }
}

