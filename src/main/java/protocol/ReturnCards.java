package protocol;

import com.google.gson.*;
import protocol.ProtocolFormat.*;

/**
 * @author Dai, Nik
 * To take off 3 cards, the client sends this kind of message
 */
public class ReturnCards implements Message {
  public String messageType;
  public String getMessageType() {
    return messageType;
  }
  public ReturnCardsBody messageBody;

  public class ReturnCardsBody extends MessageBody{
    protected String[] cards;

    public String[] getCards() {
      return cards;
    }

    public void setCards(String[] cards) {
      this.cards = cards;
    }
  }

  public ReturnCards(String[] cards){
    this.messageType = MessageType.returnCards;
    ReturnCardsBody body = new ReturnCardsBody();
    body.cards = cards;
    this.messageBody = body;
  }

  public ReturnCardsBody getMessageBody() {
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
