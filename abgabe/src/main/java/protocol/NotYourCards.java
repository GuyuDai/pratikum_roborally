package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


/**
 * @author Djafari, Dai
 */
public class NotYourCards implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public NotYourCardsBody messageBody;

    public class NotYourCardsBody extends MessageBody {
        protected int clientID;
        protected int cardsInHand;

        public int getClientID() {
            return clientID;
        }

        public int getCardsInHand() {
            return cardsInHand;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setCardsInHand(int cardsInHand) {
            this.cardsInHand = cardsInHand;
        }
    }
    public NotYourCards (int clientID, int cardsInHand) {
        this.messageType = MessageType.notYourCards;
        NotYourCardsBody body = new NotYourCardsBody();
        body.clientID = clientID;
        body.cardsInHand = cardsInHand;
        this.messageBody = body;

    }

    public NotYourCardsBody getMessageBody() {
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
