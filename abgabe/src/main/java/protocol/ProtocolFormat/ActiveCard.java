package protocol.ProtocolFormat;

import server.CardTypes.Card;

/**
 * @author Nassrin
 */
public class ActiveCard {
    public int playerId;
    public String card;
   public ActiveCard(int playerId, Card card) {
      this.playerId = playerId;
      this.card = card.getCardName();
    }
}
