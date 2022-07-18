package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class Energy extends Message {
    public EnergyBody messageBody;

    public class EnergyBody extends MessageBody {
        protected int clientID;
        protected int count;
        protected String source;

        public int getClientID() {
            return clientID;
        }

        public int getCount() {
            return count;
        }

        public String getSource() {
            return source;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
    public Energy (int clientID, int count, String source){
        this.messageType = MessageType.energy;
        EnergyBody body = new EnergyBody();
        body.clientID = clientID;
        body.count = count;
        body.source = source;
        this.messageBody = body;

    }

    public EnergyBody getMessageBody() {
        return messageBody;
    }
}
