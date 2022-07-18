package protocol;

import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class ErrorMessage extends Message {
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

}
