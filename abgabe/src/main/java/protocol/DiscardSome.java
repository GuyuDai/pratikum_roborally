package protocol;

import com.google.gson.*;
import protocol.ProtocolFormat.*;

/**
 * @author Nik, Dai
 * Shows the cards the player wants to lay on his deck
 */
public class DiscardSome implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public DiscardSomeBody messageBody;

    public class DiscardSomeBody extends MessageBody{
        protected String [] cards;
        public String[] getCards() {
            return cards;
        }

    }
    public DiscardSome(String[] cards){
        this.messageType = MessageType.discardSome;
        DiscardSome.DiscardSomeBody body = new DiscardSome.DiscardSomeBody();
        body.cards = cards;
        this.messageBody = body;
    }

    public DiscardSomeBody getMessageBody() {
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
