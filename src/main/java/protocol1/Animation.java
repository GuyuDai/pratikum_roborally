package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * For Animation of BlueConveyorBelt, GreenConveyorBelt, PushPanel, Gear, CheckPoint,
 * PlayerShooting, WallShooting,EnergySpace. Not necessary.
 */

public class Animation {

    public final String messageType = MessageType.animation;
    public MessageBody messageBody;


    public Animation (String type){
        MessageBody messageBody = new MessageBody();
        messageBody.type = type;
        this.messageBody = messageBody;

    }
    public String getMessageType() {
        return messageType;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }
}

