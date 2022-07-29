package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * @author Dai, Djafari
 * The client receives all his damage cards in one message at once.
 */

public class DrawDamage implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public DrawDamageBody messageBody;

    public class DrawDamageBody extends MessageBody {
        protected int clientID;
        protected String[] cards;

        public int getClientID() {
            return clientID;
        }

        public String[] getCards() {
            return cards;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setCards(String[] cards) {
            this.cards = cards;
        }
    }
    public DrawDamage (int clientID, String[] cards) {
        this.messageType = MessageType.drawDamage;
        DrawDamageBody body = new DrawDamageBody();
        body.clientID = clientID;
        body.cards = cards;
        this.messageBody = body;

    }

    public DrawDamageBody getMessageBody() {
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
