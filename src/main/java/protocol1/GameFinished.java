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
}
