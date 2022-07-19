package protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import protocol.Alive.AliveBody;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.Map;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;

import java.util.List;

public class GameStarted implements Message {
  public String messageType;
  public String getMessageType() {
    return messageType;
  }
  public GameStartedBody messageBody;

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

  public GameStartedBody getMessageBody() {
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
