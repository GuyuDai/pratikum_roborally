package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SetStatus extends Message {

    public class SetStatusBody extends MessageBody {
        protected boolean ready;

        public boolean isReady() {
            return ready;
        }

        public void setReady(boolean ready) {
            this.ready = ready;
        }
    }
    public SetStatus (boolean ready) {
        this.messageType = MessageType.setStatus;
        SetStatusBody body = new SetStatusBody();
        body.ready = ready;
        this.messageBody = body.toString();

    }
}
