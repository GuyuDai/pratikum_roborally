package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class PlayCard implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public PlayCardBody messageBody;

    public class PlayCardBody extends MessageBody {
        protected String card;

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }
    }
    public PlayCard (String card) {
        this.messageType = MessageType.playCard;
        PlayCardBody body = new PlayCardBody();
        body.card = card;
        this.messageBody = body;

    }

    public PlayCardBody getMessageBody() {
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
