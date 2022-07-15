package protocol;

import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.MessageType;

public class ReturnCards extends Message {
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
    this.messageBody = body.toString();
  }

}
