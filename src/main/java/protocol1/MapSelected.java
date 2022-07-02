package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class MapSelected {

    public final String messageType = MessageType.mapSelected;
    public MessageBody messageBody;

    public MapSelected (String map) {
        MessageBody messageBody = new MessageBody();
        messageBody.map =map;
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
