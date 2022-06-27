package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ActivePhase extends Message {

    private class ActivePhaseBody extends AbstractMessageBody{
        protected int phase;
    }
    public ActivePhase (int phase) {
        this.messageType = MessageType.activePhase;
        ActivePhaseBody body = new ActivePhaseBody();
        body.phase = phase;
        this.messageBody = body;
    }
}

