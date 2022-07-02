package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


public class Alive extends Message {

    private class AliveBody extends MessageBody {

    }
    public Alive (){
        this.messageType = MessageType.alive;
        AliveBody body = new AliveBody();
        this.messageBody = body;
    }


}
