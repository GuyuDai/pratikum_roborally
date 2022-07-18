package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.*;

/**
 * @author Nik
 */
public class RegisterChosen extends Message {
    public RegisterChosenBody messageBody;

    public class RegisterChosenBody extends MessageBody {
        protected int clientID;
        protected int register;

        public int getClientID() {
            return clientID;
        }
        public int getRegister(){
            return register;
        }

    }
    public RegisterChosen(int clientID, int register){
        this.messageType = MessageType.registerChosen;
        RegisterChosen.RegisterChosenBody body = new RegisterChosen.RegisterChosenBody();
        body.clientID = clientID;
        body.register = register;
        this.messageBody = body;
    }

    public RegisterChosenBody getMessageBody() {
        return messageBody;
    }
}



