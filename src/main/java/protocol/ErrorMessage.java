package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ErrorMessage implements Message {
  public String messageType;
  public String getMessageType() {
    return messageType;
  }
  public ErrorMessageBody messageBody;

  public class ErrorMessageBody extends MessageBody {
    protected String error;

    public String getError() {
      return error;
    }

    public void setError(String error) {
      this.error = error;
    }
  }
  public ErrorMessage(String str) {
    this.messageType = MessageType.error;
    ErrorMessageBody body = new ErrorMessageBody();
    body.error = str;
    this.messageBody = body;
  }

  public ErrorMessageBody getMessageBody() {
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
