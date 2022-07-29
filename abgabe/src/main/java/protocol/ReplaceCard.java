package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;
import server.CardTypes.*;

/**
 * @author Dai, Djafari
 * Some cards have to be replaced by e.g. spam cards during the activation of the register.
 */

public class ReplaceCard implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public ReplaceCardBody messageBody;

    public class ReplaceCardBody extends MessageBody {
        protected int register;
        protected String newCard;
        protected int clientID;

        public int getRegister() {
            return register;
        }

        public int getClientID() {
            return clientID;
        }

        public String getNewCard() {
            return newCard;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setRegister(int register) {
            this.register = register;
        }

        public void setNewCard(String newCard) {
            this.newCard = newCard;
        }
    }
    public ReplaceCard (int register, String newCard, int clientID) {
        this.messageType = MessageType.replaceCard;
        ReplaceCardBody body = new ReplaceCardBody();
        body.register =register;
        body.newCard = newCard;
        body.clientID = clientID;
        this.messageBody = body;

    }

    public ReplaceCardBody getMessageBody() {
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
