package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * most general message type, just send a String to client
 */
public class ClientMessage extends Message {

  private class ClientMessageBody extends MessageBody {
    protected String message;
  }
  public ClientMessage(String str){
    this.messageType = MessageType.clientMessage;
    ClientMessageBody body = new ClientMessageBody();
    body.message = str;
    this.messageBody = body;
  }
}
