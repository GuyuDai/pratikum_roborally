package protocol;

import com.google.gson.*;
import protocol.ProtocolFormat.*;

/**
 * @author Nik
 * to choose a register for the current round, the server acknowledges
 */
public class RegisterChosen implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
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



