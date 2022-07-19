package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.*;

/**
 * @author Nik
 */
public class ChooseRegister implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
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

    @Override
    public String toString(){
        Gson gson = new GsonBuilder().create();
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        //Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
