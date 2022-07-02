package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class GameFinished {

    public final String messageType = MessageType.gameFinished;
    public MessageBody messageBody;

    public GameFinished (int clientID){
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
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
