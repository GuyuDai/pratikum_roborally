package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class CardPlayed implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public CardPlayedBody messageBody;

    public class CardPlayedBody extends MessageBody {
        protected int clientID;
        protected String card;

        public int getClientID() {
            return clientID;
        }

        public String getCard() {
            return card;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setCard(String card) {
            this.card = card;
        }
    }
    public CardPlayed (int clientID, String card) {
        this.messageType = MessageType.cardPlayed;
        CardPlayedBody body = new CardPlayedBody();
        body.clientID = clientID;
        body.card = card;
        this.messageBody = body;
    }

    public CardPlayedBody getMessageBody() {
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
