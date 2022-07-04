package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ErrorMessage extends Message {

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
    this.messageBody = body.toString();
  }

}
