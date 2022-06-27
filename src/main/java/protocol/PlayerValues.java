package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class PlayerValues extends Message {

    private class PlayerValuesBody extends AbstractMessageBody {
        protected String name;
        protected int figure;
    }
    public void PlayerValue (String name, int figure) {
        this.messageType = MessageType.playerValues;
        PlayerValuesBody body = new PlayerValuesBody();
        body.name = name;
        body.figure = figure;
        this.messageBody = body;

    }
}
