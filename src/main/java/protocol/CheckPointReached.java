package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class CheckPointReached extends Message {
    public CheckPointReachedBody messageBody;

    public class CheckPointReachedBody extends MessageBody {
        protected int clientID;
        protected int number;

        public int getClientID() {
            return clientID;
        }

        public int getNumber() {
            return number;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
    public CheckPointReached (int clientID, int number){
        this.messageType = MessageType.checkpointReached;
        CheckPointReachedBody body = new CheckPointReachedBody();
        body.clientID = clientID;
        body.number = number;
        this.messageBody = body;

    }

    public CheckPointReachedBody getMessageBody() {
        return messageBody;
    }
}
