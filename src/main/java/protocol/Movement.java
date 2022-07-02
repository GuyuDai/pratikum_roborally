package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * The Movements of a client will be broadcasted to all players
 * Only movements between fields are performed. There are no changes of the Orientation.
 */

public class Movement extends Message {

    private class MovementBody extends MessageBody {
        protected int clientID;
        protected int x;
        protected int y;
    }
    public Movement (int clientID, int x, int y) {
        this.messageType = MessageType.movement;
        MovementBody body = new MovementBody();
        body.clientID = clientID;
        body.x = x;
        body.y = y;
        this.messageBody = body;

    }
}
