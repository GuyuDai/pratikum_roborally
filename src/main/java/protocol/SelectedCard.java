package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SelectedCard extends Message {

    public class SelectedCardBody extends MessageBody {
        protected String card;
        protected int register;

        public int getRegister() {
            return register;
        }

        public String getCard() {
            return card;
        }

        public void setRegister(int register) {
            this.register = register;
        }

        public void setCard(String card) {
            this.card = card;
        }
    }
    public SelectedCard(String cardName, int register){
        this.messageType = MessageType.selectedCard;
        SelectedCardBody body = new SelectedCardBody();
        body.card = cardName;
        body.register = register;
        this.messageBody = body.toString();
    }
}
