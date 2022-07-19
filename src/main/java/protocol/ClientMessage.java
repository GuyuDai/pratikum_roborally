package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * most general message type, just send a String to client
 */
public class ClientMessage implements Message {
  public String messageType;
  public String getMessageType() {
    return messageType;
  }
  public ClientMessageBody messageBody;

  public class ClientMessageBody extends MessageBody {
    protected String message;

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
  public ClientMessage(String str){
    this.messageType = MessageType.clientMessage;
    ClientMessageBody body = new ClientMessageBody();
    body.message = str;
    this.messageBody = body;
  }

  public ClientMessageBody getMessageBody() {
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
