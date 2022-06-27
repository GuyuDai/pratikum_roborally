package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * For Animation of BlueConveyorBelt, GreenConveyorBelt, PushPanel, Gear, CheckPoint,
 * PlayerShooting, WallShooting,EnergySpace. Not necessary.
 */

public class Animation extends Message {

    private class AnimationBody extends AbstractMessageBody{
        protected String type;
    }
    public Animation (String type){
        this.messageType = MessageType.animation;
        AnimationBody body = new AnimationBody();
        body.type = type;
        this.messageBody = body;

    }
}
