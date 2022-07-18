package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.*;

/**
 * @author Nik
 */
public class ChooseRegister extends Message {
    public ChooseRegisterBody messageBody;

    public class ChooseRegisterBody extends MessageBody {
        protected int register;

        public int getRegister(){
        return register;
    }

    }
    public ChooseRegister(int register){
        this.messageType = MessageType.chooseRegister;
        ChooseRegister.ChooseRegisterBody body = new ChooseRegister.ChooseRegisterBody();
        body.register = register;
        this.messageBody = body;
    }

    public ChooseRegisterBody getMessageBody() {
        return messageBody;
    }
}
