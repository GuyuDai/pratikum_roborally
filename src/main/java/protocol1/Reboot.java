package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * Reboot(): right after the register.
 * Robot start with orientation to top/north/up
 * The coordinates of the field where the robot reboots will be sent to all players via "Movement"-message.
 */

public class Reboot {

    public final String messageType = MessageType.reboot;
    public MessageBody messageBody;


    public Reboot (int clientID){
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID;
        this.messageBody = messageBody;

    }
}
