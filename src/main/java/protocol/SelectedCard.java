package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SelectedCard extends Message {

    private class SelectedCardBody extends AbstractMessageBody{
        protected String card;
        protected int register;
    }
    public SelectedCard(String cardName, int register){
        this.messageType = MessageType.selectCard;
        SelectedCardBody body = new SelectedCardBody();
        body.card = cardName;
        body.register = register;
        this.messageBody = body;
    }
}
