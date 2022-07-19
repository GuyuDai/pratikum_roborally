package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * The 30s timer starts after the one player who gets his register filled first.
 */

public class TimerStarted implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
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

    @Override
    public String toString(){
        Gson gson = new GsonBuilder().create();
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        //Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
