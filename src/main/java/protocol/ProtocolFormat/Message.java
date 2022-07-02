package protocol.ProtocolFormat;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Message{
    public String messageType;
    public MessageBody messageBody;

    @Override
    public String toString(){
        String result="";
        GsonBuilder gsonBuilder =new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Message.class,new MessageAdapter());
        Gson gson=gsonBuilder.create();
            result = gson.toJson(this);
      return result;
    }

    public String getMessageType() {
        return messageType;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }
}
