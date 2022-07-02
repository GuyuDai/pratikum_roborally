package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class CurrentPlayer {

    public final String messageType = MessageType.currentPlayer;
    public MessageBody messageBody;


    public CurrentPlayer (int clientID) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        this.messageBody = messageBody;

    }
}
