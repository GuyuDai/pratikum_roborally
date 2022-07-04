package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class StartingPointTaken extends Message {

    public class StartingPointTakenBody extends MessageBody {
        protected int x;
        protected int y;
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setX(int x) {
            this.x = x;
        }
    }
    public StartingPointTaken(int x, int y, int clientID) {
        this.messageType = MessageType.startingPointTaken;
        StartingPointTakenBody body = new StartingPointTakenBody();
        body.x = x;
        body.y = y;
        body.clientID = clientID;
        this.messageBody = body.toString();

    }
}
