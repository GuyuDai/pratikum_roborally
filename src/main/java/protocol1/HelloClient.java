package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;
import protocol1.ProtocolFormat.Message;


public class HelloClient extends Message {
  public final String messageType = MessageType.helloClient;
  public MessageBody messageBody;


  public HelloClient (String protocol) {
    MessageBody messageBody = new MessageBody();
    messageBody.protocol = protocol;
    this.messageBody = messageBody;
  }
  public String getMessageType() {
    return messageType;
  }

  public MessageBody getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(MessageBody messageBody) {
    this.messageBody = messageBody;
  }
}

