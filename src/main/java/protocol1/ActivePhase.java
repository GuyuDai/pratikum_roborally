package protocol1;

import client.Client;
import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

public class ActivePhase {

    public final String messageType = MessageType.activePhase;
    public MessageBody messageBody;


    public ActivePhase (int phase) {
        MessageBody messageBody = new MessageBody();
        messageBody.phase = phase;
        this.messageBody = messageBody;
    }


    public MessageBody getMessageBody() {
        return messageBody;
    }
    public String getMessageType() {
        return messageType;
    }


}

