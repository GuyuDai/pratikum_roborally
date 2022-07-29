package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * @author Djafari, Dai
 * The server sends the filled registers without any information about the cards to all players.
 * "Filled" is saying whether a card was put to/ removed from the register.
 */

public class CardSelected implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public CardSelectedBody messageBody;

    public class CardSelectedBody extends MessageBody {
        protected int clientID;
        protected int register;
        protected boolean filled;

        public int getClientID() {
            return clientID;
        }

        public int getRegister() {
            return register;
        }

        public boolean isFilled() {
            return filled;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setRegister(int register) {
            this.register = register;
        }

        public void setFilled(boolean filled) {
            this.filled = filled;
        }
    }

    public CardSelected (int clientID, int register, boolean filled) {
        this.messageType = MessageType.cardSelected;
        CardSelectedBody body = new CardSelectedBody();
        body.clientID = clientID;
        body.register = register;
        body.filled = filled;
        this.messageBody = body;

    }

    public CardSelectedBody getMessageBody() {
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
