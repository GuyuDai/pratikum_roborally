package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SetStatus extends Message {

    private class SetStatusBody extends MessageBody {
        protected boolean ready;
    }
    public SetStatus (boolean ready) {
        this.messageType = MessageType.setStatus;
        SetStatusBody body = new SetStatusBody();
        body.ready = ready;
        this.messageBody = body;

    }
}
