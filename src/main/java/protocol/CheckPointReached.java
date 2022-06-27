package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class CheckPointReached extends Message {

    private class CheckPointReachedBody extends AbstractMessageBody{
        protected int clientID;
        protected int number;
    }
    public CheckPointReached (int clientID, int number){
        this.messageType = MessageType.checkpointReached;
        CheckPointReachedBody body = new CheckPointReachedBody();
        body.clientID = clientID;
        body.number = number;
        this.messageBody = body;

    }
}
