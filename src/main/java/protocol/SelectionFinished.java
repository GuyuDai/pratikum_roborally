package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

/**
 * As soon as the player selects his 5th card, no more adjustments can be made.
 * This will be sent to all players.
 */

public class SelectionFinished implements Message {
    public String messageType;
    public String getMessageType() {
        return messageType;
    }
    public SelectedFinishedBody messageBody;

    public class SelectedFinishedBody extends MessageBody {
        protected int clientID;

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }
    }

    public SelectionFinished (int clientID) {
        this.messageType = MessageType.selectionFinished;
        SelectedFinishedBody body = new SelectedFinishedBody();
        body.clientID = clientID;
        this.messageBody = body;

    }

    public SelectedFinishedBody getMessageBody() {
        return messageBody;
    }

    @Override
    public String toString(){
        Gson gson = new GsonBuilder().create();
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        //Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
