package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class PlayerAdded {

    public final String messageType = MessageType.playerAdded;
    public MessageBody messageBody;



    public PlayerAdded (int clientID, String name, int figure) {
        MessageBody messageBody = new MessageBody();
        messageBody.clientID = clientID ;
        messageBody.name = name;
        messageBody.figure = figure;
        this.messageBody = messageBody;

    }
}
