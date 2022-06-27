package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class MapSelected extends Message {

    private class MapSelectedBody extends AbstractMessageBody{
        protected String map;
    }
    public MapSelected (String map) {
        this.messageType = MessageType.mapSelected;
        MapSelectedBody body = new MapSelectedBody();
        body.map = map;
        this.messageBody = body;

    }
}
