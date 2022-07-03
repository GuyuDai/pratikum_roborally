package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SelectMap extends Message {

    public class SelectMapBody extends MessageBody {
        protected String[] availableMaps;

        public String[] getAvailableMaps() {
            return availableMaps;
        }

        public void setAvailableMaps(String[] availableMaps) {
            this.availableMaps = availableMaps;
        }
    }

    public SelectMap(String[] availableMaps) {
        this.messageType = MessageType.selectMap;
        SelectMapBody body = new SelectMapBody();
        body.availableMaps = availableMaps ;
        this.messageBody = body;

    }
}
