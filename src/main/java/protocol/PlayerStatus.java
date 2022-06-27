package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class PlayerStatus extends Message {

    private class PlayerStatusBody extends AbstractMessageBody{
        protected int clientID;
        protected boolean ready;
    }
    public PlayerStatus (int clientID, boolean ready) {
        this.messageType = MessageType.playerStatus;
        PlayerStatusBody body = new PlayerStatusBody();
        body.clientID = clientID;
        body.ready = ready;
        this.messageBody = body;

    }
}

