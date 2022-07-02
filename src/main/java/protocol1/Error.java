package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class Error {


    public final String messageType = MessageType.error;
    public MessageBody messageBody;


    public Error (String error) {
        MessageBody messageBody = new MessageBody();
        messageBody.error = error;
        this.messageBody = messageBody;

    }
}
