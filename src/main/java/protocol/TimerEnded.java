package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * All clients get a message after the timer ends.
 * The message also informs about players who might have been too slow.
 */

public class TimerEnded implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public TimerEndedBody messageBody;

    public class TimerEndedBody extends MessageBody {
        protected int[] clientIDs;

        public int[] getClientIDs() {
            return clientIDs;
        }

        public void setClientIDs(int[] clientIDs) {
            this.clientIDs = clientIDs;
        }
    }
    public TimerEnded (int[] clientIDs) {
        this.messageType = MessageType.timerEnded;
        TimerEndedBody body = new TimerEndedBody();
        body.clientIDs = clientIDs;
        this.messageBody = body;

    }

    public TimerEndedBody getMessageBody() {
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
