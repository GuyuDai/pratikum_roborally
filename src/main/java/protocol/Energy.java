package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class Energy extends Message {

    private class EnergyBody extends AbstractMessageBody{
        protected int clientID;
        protected int count;
        protected String source;
    }
    public Energy (int clientID, int count, String source){
        this.messageType = MessageType.energy;
        EnergyBody body = new EnergyBody();
        body.clientID = clientID;
        body.count = count;
        body.source = source;
        this.messageBody = body;

    }
}
