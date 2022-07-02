package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;


public class Alive {

    public fi String messageType = MessageType.alive;
    public MessageBody messageBody;


    public Alive (){
        MessageBody messagebody = new MessageBody();
        this.messageBody = messageBody;
    }


}
