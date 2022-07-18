package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ActivePhase extends Message {
    public ActivePhaseBody messageBody;
    public class ActivePhaseBody extends MessageBody {
        protected int phase;

        public int getPhase() {
            return phase;
        }

        public void setPhase(int phase) {
            this.phase = phase;
        }
    }
    public ActivePhase (int phase) {
        this.messageType = MessageType.activePhase;
        ActivePhaseBody body = new ActivePhaseBody();
        body.phase = phase;
        this.messageBody = body;
    }

    public ActivePhaseBody getMessageBody() {
        return messageBody;
    }
}

