package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * @author Dai, Djafari
 * Dropping all cards and pulling cards blindly: This will happen if a player was too slow in filling his register.
 * Empty registers will be filled automatically with the players cardsInHand in the given order.
 */

public class CardsYouGotNow implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public CardYouGotNowBody messageBody;

    public class CardYouGotNowBody extends MessageBody {
        protected String[] cards;

        public String[] getCards() {
            return cards;
        }

        public void setCards(String[] cards) {
            this.cards = cards;
        }
    }
    public CardsYouGotNow (String[] cards){
        this.messageType = MessageType.cardsYouGotNow;
        CardYouGotNowBody body = new CardYouGotNowBody();
        body.cards = cards;
        this.messageBody = body;

    }

    public CardYouGotNowBody getMessageBody() {
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
