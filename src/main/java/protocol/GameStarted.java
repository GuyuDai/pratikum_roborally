package protocol;

import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Map;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

import java.util.List;

public class GameStarted extends Message {

  public class GameStartedBody extends MessageBody {
    protected List<Map> gameMap;

    public List<Map> getGameMap() {
      return gameMap;
    }

    public void setGameMap(List<Map> gameMap) {
      this.gameMap = gameMap;
    }
  }

    public GameStarted (List<Map> gameMap) {
      this.messageType = MessageType.gameStarted;
      GameStartedBody body = new GameStartedBody();
      body.gameMap = gameMap;
      this.messageBody = body;
    }
}
