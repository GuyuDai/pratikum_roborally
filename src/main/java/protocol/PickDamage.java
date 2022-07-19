package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * PickDamage()
 * The SpamCards are count. If all SpamCards are taken the server asks the client to pick a damageCard.
 * After selecting the damageCard the server will announce the damage.
 * availablePiles is the pile of cards which contains damageCard.
 */

public class PickDamage implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public PickDamageBody messageBody;

    public class PickDamageBody extends MessageBody {
        protected int count;
        protected String[] availablePiles;

        public int getCount() {
            return count;
        }

        public String[] getAvailablePiles() {
            return availablePiles;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setAvailablePiles(String[] availablePiles) {
            this.availablePiles = availablePiles;
        }
    }
    public PickDamage (int count, String[] availablePiles){
        this.messageType = MessageType.pickDamage;
        PickDamageBody body = new PickDamageBody();
        body.count = count;
        body.availablePiles = availablePiles;
        this.messageBody = body;

    }

    public PickDamageBody getMessageBody() {
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
