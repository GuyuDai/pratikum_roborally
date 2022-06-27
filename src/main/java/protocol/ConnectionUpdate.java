package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ConnectionUpdate extends Message {

    private class ConnectionUpdateBody extends AbstractMessageBody{
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
