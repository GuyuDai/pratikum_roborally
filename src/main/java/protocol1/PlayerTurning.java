package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

import java.util.ArrayList;

/**
 * The direction of a turning is described as "clockwise" and "counterclockwise".
 */

public class PlayerTurning {

    public final String messageType = MessageType.playerTurning;
    public MessageBody messageBody;


    public PlayerTurning (int clientID, String rotation){
        MessageBody messageBody = new MessageBody();
        messageBody.clientID  = clientID;
        messageBody.rotation = rotation;
        this.messageBody = messageBody;

    }
}
