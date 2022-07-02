package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

public class PlayerValues extends Message {

    public class PlayerValuesBody extends MessageBody {
        protected String name;
        protected int figure;

        public int getFigure() {
            return figure;
        }

        public String getName() {
            return name;
        }

        public void setFigure(int figure) {
            this.figure = figure;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public void PlayerValue (String name, int figure) {
        this.messageType = MessageType.playerValues;
        PlayerValuesBody body = new PlayerValuesBody();
        body.name = name;
        body.figure = figure;
        this.messageBody = body;

    }
}
