package protocol;

import protocol.ProtocolFormat.*;

public class CheckPointMoved extends Message {

    public class CheckPointMovedBody extends MessageBody{


        protected int checkPointID;
        protected int x;
        protected int y;

        public int getCheckPointID() {
            return checkPointID;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }

    }

    public CheckPointMoved (int checkPointID, int x, int y){
        this.messageType = MessageType.checkPointMoved;
        CheckPointMoved.CheckPointMovedBody body = new CheckPointMoved.CheckPointMovedBody();
        body.checkPointID = checkPointID;
        body.x = x;
        body.y = y;
        this.messageBody = body.toString();
    }
}
