package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SetStatus extends Message {

    private class SetStatusBody extends AbstractMessageBody{
        protected boolean ready;
    }
    public SetStatus (boolean ready) {
        this.messageType = MessageType.setStatus;
        SetStatusBody body = new SetStatusBody();
        body.ready = ready;
        this.messageBody = body;

    }
}
