package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class HelloClient extends Message {

  private class HelloClientBody extends MessageBody {
    protected String protocol;
  }
  public HelloClient(String str){
    this.messageType = MessageType.helloClient;
    HelloClientBody body = new HelloClientBody();
    body.protocol = str;

    this.messageBody = body;
    //this.messageBody = body.toString();
  }

  /*
  @Override
  public String toString(){
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(this.getClass(), new HelloClientAdapter());
    gsonBuilder.setPrettyPrinting();
    final Gson gson = gsonBuilder.create();

    return gson.toJson(this);
  }

   */
}
