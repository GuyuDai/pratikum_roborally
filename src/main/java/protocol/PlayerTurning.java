package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * The direction of a turning is described as "clockwise" and "counterclockwise".
 */

public class PlayerTurning extends Message {

    private class PlayerTurningBody extends AbstractMessageBody{
        protected int clientID;
        protected String rotation;
    }
    public PlayerTurning (int clientID, String rotation){
        this.messageType = MessageType.playerTurning;
        PlayerTurningBody body = new PlayerTurningBody();
        body.clientID  = clientID;
        body.rotation = rotation;
        this.messageBody = body;

    }
}
