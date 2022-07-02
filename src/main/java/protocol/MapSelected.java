package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class MapSelected extends Message {

    private class MapSelectedBody extends MessageBody {
        protected String map;
    }
    public MapSelected (String map) {
        this.messageType = MessageType.mapSelected;
        MapSelectedBody body = new MapSelectedBody();
        body.map = map;
        this.messageBody = body;

    }
}
