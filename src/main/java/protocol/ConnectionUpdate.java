package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ConnectionUpdate extends Message {
    public ConnectionUpdateBody messageBody;

    public class ConnectionUpdateBody extends MessageBody {
        protected int clientID;
        protected boolean isConnected;
        protected String action;

        public int getClientID() {
            return clientID;
        }

        public String getAction() {
            return action;
        }

        public boolean isConnected() {
            return isConnected;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public void setConnected(boolean connected) {
            isConnected = connected;
        }
    }
    public ConnectionUpdate (int clientID, boolean isConnected, String action) {
        this.messageType = MessageType.connectionUpdate;
        ConnectionUpdateBody body = new ConnectionUpdateBody();
        body.clientID = clientID;
        body.isConnected = isConnected;
        body.action = action;
        this.messageBody = body;

    }

    public ConnectionUpdateBody getMessageBody() {
        return messageBody;
    }
}
