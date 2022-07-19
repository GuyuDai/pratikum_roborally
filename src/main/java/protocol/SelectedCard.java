package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SelectedCard implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public SelectedCardBody messageBody;

    public class SelectedCardBody extends MessageBody {
        protected String card;
        protected int register;
        protected  int clientID;
        public int getRegister() {
            return register;
        }

        public String getCard() {
            return card;
        }

        public int getClientID(){
            return clientID;
        }

        public void setRegister(int register) {
            this.register = register;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public void setClientID(int clientID){this.clientID= clientID;}
    }
    public SelectedCard(String cardName, int register,int clientID){
        this.messageType = MessageType.selectedCard;
        SelectedCardBody body = new SelectedCardBody();
        body.card = cardName;
        body.register = register;
        body.clientID=clientID;
        this.messageBody = body;
    }

    public SelectedCardBody getMessageBody() {
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
