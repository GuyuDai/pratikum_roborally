package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SelectMap extends Message {

    private class SelectMapBody extends AbstractMessageBody{
        protected String[] availableMaps;
    }

    public SelectMap(String[] availableMaps) {
        this.messageType = MessageType.selectMap;
        SelectMapBody body = new SelectMapBody();
        body.availableMaps = availableMaps ;
        this.messageBody = body;

    }
}
