package protocol.ProtocolFormat;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Message{
    public String messageType;

    @Override
    public String toString(){
        Gson gson = new GsonBuilder().create();
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        //Gson gson = gsonBuilder.create();
        return gson.toJson(this);

    }


    public String getMessageType() {
        return messageType;
    }

}
