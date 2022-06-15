package transfer;

import java.util.*;



public class Player {



    private final String name;

    /*
     private Card[] handCards;
     private List<Card> discardedCards;
     */

     private int hearts = 0;
     private boolean inGame;
     private boolean guarded;
     private String cardName;


    public Player(String name) {
        this.name = name;
        /*
         handCards = new Card[2];
         handCards[0] = null;
         handCards[1] = null;
         */

         inGame = true;
         guarded = false;

    }

    public String getName() {return name;}

    /*
     public int getHearts() {return hearts;}

     public void setHearts() {this.hearts ++;}


     public boolean isGuarded() {return guarded;}

     public void setGuarded(boolean defended) {this.guarded = defended;}

     public boolean isInGame() {return inGame;}

     public void setInGame(boolean inGame) {this.inGame = inGame;}

     public Card[] getHandCards() {return handCards;}

     public Card getHandCard(int index) {return handCards[index];}

     public List<Card> getDiscardedCards() {return discardedCards;}

     public void setHandCard(int index, Card card) {handCards[index] = card;}

     public void addHandCard(CardStack cardStack,int index) {
     Card card = cardStack.drawCard();
     handCards[index] = card;
     }

     public void addDiscardedCard(Card card) {discardedCards.add(card);}

     public void removeCard(Card card) {
     Card tempCard;
     if(handCards[0].getName().equals(card.getName())) {
     tempCard = handCards[1];
     handCards[0] = tempCard;
     handCards[1] = null;
     }else {
     handCards[1] = null;
     }
     }


     public boolean checkCard(Card card) {
     if(handCards[0].getValue()==card.getValue()) {
     return true;
     }else if(handCards[1].getValue()==card.getValue()) {
     return true;
     }else {
     return false;
     }
     }
     */


}
