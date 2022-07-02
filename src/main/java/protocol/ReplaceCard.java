package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * Some cards have to be replaced by e.g. spam cards during the activation of the register.
 */

public class ReplaceCard extends Message {

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
}
