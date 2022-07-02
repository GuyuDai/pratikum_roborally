package protocol.ProtocolFormat;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Message{
    public String messageType;
    public AbstractMessageBody messageBody;

    @Override
    public String toString(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = "";
        if(messageType.equals("ClientMessage")){
            result = gson.toJson(messageBody);
        }else{
            result = gson.toJson(this);
        }
        return result;
    }

    public String getMessageType() {
        return messageType;
    }

    public AbstractMessageBody getMessageBody() {
        return messageBody;
    }
}
