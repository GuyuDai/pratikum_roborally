package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * The 30s timer starts after the one player who gets his register filled first.
 */

public class TimerStarted extends Message {
    public TimerStartedBody messageBody;

    public class TimerStartedBody extends MessageBody {
    }
    public TimerStarted () {
        this.messageType = MessageType.timerStarted;
        TimerStartedBody body = new TimerStartedBody();
        this.messageBody = body;

    }

    public TimerStartedBody getMessageBody() {
        return messageBody;
    }
}
