package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * For Animation of BlueConveyorBelt, GreenConveyorBelt, PushPanel, Gear, CheckPoint,
 * PlayerShooting, WallShooting,EnergySpace. Not necessary.
 */

public class Animation extends Message {

    public class AnimationBody extends MessageBody {
        protected String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    public Animation (String type){
        this.messageType = MessageType.animation;
        AnimationBody body = new AnimationBody();
        body.type = type;
        this.messageBody = body.toString();

    }
}
