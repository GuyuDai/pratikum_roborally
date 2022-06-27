package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * PickDamage()
 * The SpamCards are count. If all SpamCards are taken the server asks the client to pick a damageCard.
 * After selecting the damageCard the server will announce the damage.
 * availablePiles is the pile of cards which contains damageCard.
 */

public class PickDamage extends Message {

    private class PickDamageBody extends AbstractMessageBody{
        protected int count;
        protected String[] availablePiles;
    }
    public PickDamage (int count, String[] availablePiles){
        this.messageType = MessageType.pickDamage;
        PickDamageBody body = new PickDamageBody();
        body.count = count;
        body.availablePiles = availablePiles;
        this.messageBody = body;

    }
}
