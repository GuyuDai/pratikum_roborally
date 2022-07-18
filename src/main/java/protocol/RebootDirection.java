package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;


public class RebootDirection extends Message {
    public RebootDirectionBody messageBody;

    public class RebootDirectionBody extends MessageBody {
        protected String direction;

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
    }
    public RebootDirection (String direction){
        this.messageType = MessageType.rebootDirection;
        RebootDirectionBody body = new RebootDirectionBody();
        body.direction = direction;
        this.messageBody = body;

    }

    public RebootDirectionBody getMessageBody() {
        return messageBody;
    }
}
