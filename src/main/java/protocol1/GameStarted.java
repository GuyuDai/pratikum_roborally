package protocol1;

import protocol1.ProtocolFormat.MessageBody;
import protocol1.ProtocolFormat.MessageType;
import server.BoardElement.BoardElem;


import java.util.ArrayList;

public class GameStarted {

    public final String messageType = MessageType.gameStarted;
    public MessageBody messageBody;


    public GameStarted (ArrayList<ArrayList<ArrayList<BoardElem>>> gameMap) {
            messageBody = new MessageBody();
            messageBody.gameMap = gameMap;
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
