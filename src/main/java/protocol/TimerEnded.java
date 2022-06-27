package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * All clients get a message after the timer ends.
 * The message also informs about players who might have been too slow.
 */

public class TimerEnded extends Message {

    private class TimerEndedBody extends AbstractMessageBody{
        protected int[] clientIDs;
    }
    public TimerEnded (int[] clientIDs) {
        this.messageType = MessageType.timerEnded;
        TimerEndedBody body = new TimerEndedBody();
        body.clientIDs = clientIDs;
        this.messageBody = body;

    }

}
