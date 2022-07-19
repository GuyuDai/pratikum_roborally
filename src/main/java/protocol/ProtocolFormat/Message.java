package protocol.ProtocolFormat;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface Message{
    public String getMessageType();
    public MessageBody getMessageBody();

}
