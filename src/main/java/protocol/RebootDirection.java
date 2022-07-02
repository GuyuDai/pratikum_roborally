package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


public class RebootDirection extends Message {

    private class RebootDirectionBody extends MessageBody {
        protected String direction;
    }
    public RebootDirection (String direction){
        this.messageType = MessageType.rebootDirection;
        RebootDirectionBody body = new RebootDirectionBody();
        body.direction = direction;
        this.messageBody = body;

    }
}
