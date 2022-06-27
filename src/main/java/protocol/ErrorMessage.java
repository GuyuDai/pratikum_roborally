package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ErrorMessage extends Message {

  private class ErrorMessageBody extends AbstractMessageBody{
    protected String error;
  }
  public ErrorMessage(String str) {
    this.messageType = MessageType.error;
    ErrorMessageBody body = new ErrorMessageBody();
    body.error = str;
    this.messageBody = body;
  }

}
