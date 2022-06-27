package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class CurrentPlayer extends Message {

    private class CurrentPlayerBody extends AbstractMessageBody{
        protected int clientID;
    }
    public CurrentPlayer (int clientID) {
        this.messageType = MessageType.currentPlayer;
        CurrentPlayerBody body = new CurrentPlayerBody();
        body.clientID = clientID;
        this.messageBody = body;

    }
}
