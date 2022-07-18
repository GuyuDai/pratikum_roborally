package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * most general message type, just send a String to client
 */
public class ClientMessage extends Message {
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
}
