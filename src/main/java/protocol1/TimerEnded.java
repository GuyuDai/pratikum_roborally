package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * All clients get a message after the timer ends.
 * The message also informs about players who might have been too slow.
 */

public class TimerEnded {

    public final String messageType = MessageType.timerEnded;
    public MessageBody messageBody;


    public TimerEnded (int[] clientIDs) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientIDs = clientIDs;
        this.messageBody = messageBody;

    }

}
