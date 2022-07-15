package protocol;

import protocol.ProtocolFormat.*;

public class Boink extends Message{
    public class BoinkBody extends MessageBody{
        protected String orientation;

        public String getOrientation(){
            return orientation;
        }
    }

    public Boink (String orientation){
        this.messageType = MessageType.boink;
        Boink.BoinkBody body = new Boink.BoinkBody();
        body.orientation = orientation;
        this.messageBody = body.toString();
    }

}
