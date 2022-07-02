package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class CardPlayed {

    public final String messageType = MessageType.cardPlayed;
    public MessageBody messageBody;


    public CardPlayed (int clientID, String card) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        messageBody.card = card;
        this.messageBody = messageBody;

    }
    public String getMessageType() {
        return messageType;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }
}