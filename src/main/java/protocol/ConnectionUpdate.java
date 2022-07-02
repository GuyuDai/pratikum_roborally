package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ConnectionUpdate extends Message {

    private class ConnectionUpdateBody extends MessageBody {
        protected int clientID;
        protected boolean isConnected;
        protected String action;
    }
    public ConnectionUpdate (int clientID, boolean isConnected, String action) {
        this.messageType = MessageType.connectionUpdate;
        ConnectionUpdateBody body = new ConnectionUpdateBody();
        body.clientID = clientID;
        body.isConnected = isConnected;
        body.action = action;
        this.messageBody = body;

    }
}
