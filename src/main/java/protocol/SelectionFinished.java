package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * As soon as the player selects his 5th card, no more adjustments can be made.
 * This will be sent to all players.
 */

public class SelectionFinished extends Message {
    public SelectedFinishedBody messageBody;

    public class SelectedFinishedBody extends MessageBody {
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }
    }

    public SelectionFinished (int clientID) {
        this.messageType = MessageType.selectionFinished;
        SelectedFinishedBody body = new SelectedFinishedBody();
        body.clientID = clientID;
        this.messageBody = body;

    }

    public SelectedFinishedBody getMessageBody() {
        return messageBody;
    }
}
