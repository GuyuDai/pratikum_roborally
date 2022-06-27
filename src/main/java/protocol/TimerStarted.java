package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * The 30s timer starts after the one player who gets his register filled first.
 */

public class TimerStarted extends Message {

    private class TimerStartedBody extends AbstractMessageBody{
    }
    public TimerStarted () {
        this.messageType = MessageType.timerStarted;
        TimerStartedBody body = new TimerStartedBody();
        this.messageBody = body;

    }
}
