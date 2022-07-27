package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * @author Dai
 *
 * The first 9 programming cards are drawn and given to a player.
 * This message is only sent to the player receiving the programming cards.
 */

public class YourCards implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public YourCardsBody messageBody;

    public class YourCardsBody extends MessageBody {
        protected String[] cardsInHand;

        public String[] getCardsInHand() {
            return cardsInHand;
        }

        public void setCardsInHand(String[] cardsInHand) {
            this.cardsInHand = cardsInHand;
        }
    }

    /**
     * Protocol for yourCards
     */
    public YourCards (String[] cardsInHand) {
        this.messageType = MessageType.yourCards;
        YourCardsBody body = new YourCardsBody();
        body.cardsInHand = cardsInHand;
        this.messageBody = body;

    }

    /**
     * MessageBody for yourCards
     */
    public YourCardsBody getMessageBody() {
        return messageBody;
    }

    /**
     * Json
     */
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
