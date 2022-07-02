package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SetStartingPoint extends Message {

    private class SetStartingPointBody extends MessageBody {
        protected int x;
        protected int y;
    }
    public SetStartingPoint (int x, int y) {
        this.messageType = MessageType.setStartingPoint;
        SetStartingPointBody body = new SetStartingPointBody();
        body.x=x;
        body.y=y;
        this.messageBody = body;

    }
}
