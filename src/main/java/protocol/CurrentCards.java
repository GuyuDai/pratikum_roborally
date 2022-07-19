package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.ActiveCard;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

import java.util.List;


/**
 * Active programming cards
 * In each round a message announcing the current active cards will be sent to all players.
 * Only the cards of the current register will be announced.
 * The priorities of the cards have to be recalculated after each register.
 */

public class CurrentCards implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public CurrentCardsBody messageBody;

    public class CurrentCardsBody extends MessageBody {
        protected ActiveCard[] activeCards;

        public ActiveCard[] getActiveCards() {
            return activeCards;
        }

        public void setActiveCards(ActiveCard[] activeCards) {
            this.activeCards = activeCards;
        }
    }
    public CurrentCards (ActiveCard[] activeCards) {
        this.messageType = MessageType.currentCards;
        CurrentCardsBody body = new CurrentCardsBody();
        body.activeCards = activeCards;
        this.messageBody = body;

    }

    public CurrentCardsBody getMessageBody() {
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
