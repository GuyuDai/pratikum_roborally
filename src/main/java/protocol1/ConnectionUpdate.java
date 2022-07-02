package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class ConnectionUpdate {

    public final String messageType = MessageType.connectionUpdate;
    public MessageBody messageBody;


    public ConnectionUpdate (int clientID, boolean isConnected, String action) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        messageBody.isConnected = isConnected;
        messageBody.action = action;
        this.messageBody = messageBody;

    }
    public String getMessageType() {
        return messageType;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }
}
