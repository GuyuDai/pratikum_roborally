package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * Reboot(): right after the register.
 * Robot start with orientation to top/north/up
 * The coordinates of the field where the robot reboots will be sent to all players via "Movement"-message.
 */

public class Reboot extends Message {

    public class RebootBody extends MessageBody {
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }
    }

    public Reboot (int clientID){
        this.messageType = MessageType.reboot;
        RebootBody body = new RebootBody();
        body.clientID = clientID;
        this.messageBody = body;

    }
}
