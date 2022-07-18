package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class SetStartingPoint extends Message {
    public SetStartingPointBody messageBody;

    public class SetStartingPointBody extends MessageBody {
        protected int x;
        protected int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
    public SetStartingPoint (int x, int y) {
        this.messageType = MessageType.setStartingPoint;
        SetStartingPointBody body = new SetStartingPointBody();
        body.x=x;
        body.y=y;
        this.messageBody = body;

    }

    public SetStartingPointBody getMessageBody() {
        return messageBody;
    }
}
