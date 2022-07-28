package protocol.ProtocolFormat;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Djafari, Dai
 */
public interface Message{
    public String getMessageType();
    public MessageBody getMessageBody();

}
