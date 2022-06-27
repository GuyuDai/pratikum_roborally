package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class GameFinished extends Message {

    private class GameFinishedBody extends AbstractMessageBody{
        protected int clientID;
    }
    public GameFinished (int clientID){
        this.messageType = MessageType.gameFinished;
        GameFinishedBody body = new GameFinishedBody();
        body.clientID = clientID;
        this.messageBody = body;

    }
}
