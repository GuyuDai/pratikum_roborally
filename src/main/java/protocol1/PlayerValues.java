package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class PlayerValues {


    public final String messageType = MessageType.playerValues;
    public MessageBody messageBody;

    public void PlayerValue (String name, int figure) {
        MessageBody messageBody = new MessageBody();
        messageBody.name = name;
        messageBody.figure = figure;
        this.messageBody = messageBody;

    }
}
