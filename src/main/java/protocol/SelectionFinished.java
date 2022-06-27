package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * As soon as the player selects his 5th card, no more adjustments can be made.
 * This will be sent to all players.
 */

public class SelectionFinished extends Message {

    private class SelectedFinishedBody extends AbstractMessageBody{
        protected int clientID;
    }

    public SelectionFinished (int clientID) {
        this.messageType = MessageType.selectionFinished;
        SelectedFinishedBody body = new SelectedFinishedBody();
        body.clientID = clientID;
        this.messageBody = body;

    }
}
