package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class PlayerAdded extends Message {

    public class PlayerAddedBody extends MessageBody {
        protected int clientID;
        protected String name;
        protected int figure;

        public int getClientID() {
            return clientID;
        }

        public String getName() {
            return name;
        }

        public int getFigure() {
            return figure;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setFigure(int figure) {
            this.figure = figure;
        }
    }
    public PlayerAdded (int clientID, String name, int figure) {
        this.messageType = MessageType.playerAdded;
        PlayerAddedBody body = new PlayerAddedBody();
        body.clientID = clientID ;
        body.name = name;
        body.figure = figure;
        this.messageBody = body.toString();

    }
}
