package protocol;

import protocol.ProtocolFormat.AbstractMessageBody;
import protocol.ProtocolFormat.Map;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

import java.util.List;

public class GameStarted extends Message {

  private class GameStartedBody extends AbstractMessageBody{
    protected List<Map> gameMap;
  }

    public GameStarted (List<Map> gameMap) {
      this.messageType = MessageType.gameStarted;
      GameStartedBody body = new GameStartedBody();
      body.gameMap = gameMap;
      this.messageBody = body;
    }
}
