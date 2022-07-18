package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SelectedCard extends Message {
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
}
