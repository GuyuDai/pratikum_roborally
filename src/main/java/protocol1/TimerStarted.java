package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * The 30s timer starts after the one player who gets his register filled first.
 */

public class TimerStarted {

    public final String messageType = MessageType.timerStarted;
    public MessageBody messageBody;


    public TimerStarted () {
        MessageBody messageBody = new MessageBody();
        this.messageBody = messageBody;

    }
}
