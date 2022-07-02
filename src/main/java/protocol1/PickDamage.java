package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;

/**
 * PickDamage()
 * The SpamCards are count. If all SpamCards are taken the server asks the client to pick a damageCard.
 * After selecting the damageCard the server will announce the damage.
 * availablePiles is the pile of cards which contains damageCard.
 */

public class PickDamage {

    public final String messageType = MessageType.pickDamage;
    public MessageBody messageBody;


    public PickDamage (int count, String[] availablePiles){
        MessageBody messageBody = new MessageBody();
        messageBody.count = count;
        messageBody.availablePiles = availablePiles;
        this.messageBody = messageBody;

    }
}
