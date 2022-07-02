package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class SelectMap {


    public final String messageType = MessageType.selectMap;
    public MessageBody messageBody;

    public SelectMap(String[] availableMaps) {
        MessageBody messageBody = new MessageBody();
        messageBody.availableMaps = availableMaps ;
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