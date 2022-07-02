package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class PlayerStatus {

    public final String messageType = MessageType.playerStatus;
    public MessageBody messageBody;



    public PlayerStatus (int clientID, boolean ready) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        messageBody.ready = ready;
        this.messageBody = messageBody;

    }
}

