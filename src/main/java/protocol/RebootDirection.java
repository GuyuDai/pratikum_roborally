package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


public class RebootDirection extends Message {

    private class RebootDirectionBody extends AbstractMessageBody{
        protected String direction;
    }
    public RebootDirection (String direction){
        this.messageType = MessageType.rebootDirection;
        RebootDirectionBody body = new RebootDirectionBody();
        body.direction = direction;
        this.messageBody = body;

    }
}
