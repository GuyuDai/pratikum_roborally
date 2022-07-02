package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class StartingPointTaken extends Message {

    private class StartingPointTakenBody extends MessageBody {
        protected int x;
        protected int y;
        protected int clientID;
    }
    public StartingPointTaken(int x, int y, int clientID) {
        this.messageType = MessageType.startingPointTaken;
        StartingPointTakenBody body = new StartingPointTakenBody();
        body.x = x;
        body.y = y;
        body.clientID = clientID;
        this.messageBody = body;

    }
}
