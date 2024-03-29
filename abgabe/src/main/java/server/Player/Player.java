package server.Player;

import protocol.DiscardSome;
import protocol.DrawDamage;
import protocol.ErrorMessage;
import protocol.PickDamage;
import server.CardTypes.*;
import server.Deck.*;
import server.Game.*;

import java.util.*;
import java.util.concurrent.*;


/**
 * @author dai
 * client, who participaints in the game and has player's behavior
 */
public class Player implements PlayerAction{
  public String name;
  public int clientID;
  private RR currentGame;
  private ProgrammingDeck ownDeck;
  public Robot ownRobot;
  private CopyOnWriteArrayList<Card> hands = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<Card> register = new CopyOnWriteArrayList<>();
  private int priority = 1;
  public int energyCubes;
  private CopyOnWriteArrayList<Integer> checkPoints = new CopyOnWriteArrayList<Integer>();
  private boolean isAI = false;
  private boolean isReady = false;
  private static final String[] AINameArray = new String[] {"AIBob","AIAlice","AIJay","AIMax","AICarl","AITom"};



  public Player(String name, int ID){
    this.name = name;
    this.clientID = ID;
    this.energyCubes = 5;
    this.ownDeck = new ProgrammingDeck();
    for(Card card : ownDeck.getRemainingCards()){
      card.setOwner(this);
    }
  }

  public Robot getOwnRobot() {
    return ownRobot;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getClientId(){
    return clientID;
  }
  public boolean getReady() {
    return isReady;
  }
  public void setReady(boolean ready) {
    isReady = ready;
  }
  public RR getCurrentGame() {
    return currentGame;
  }
  public void setCurrentGame(RR currentGame) {
    this.currentGame = currentGame;
  }
  public CopyOnWriteArrayList<Card> getHands() {
    return hands;
  }
  public int getEnergyCubes(){
    return energyCubes;
  }
  public void setEnergyCubes(int energyCubes) {
    this.energyCubes = energyCubes;
  }
  public CopyOnWriteArrayList<Integer> getCheckPoints() {
    return checkPoints;
  }
  public void setCheckPoints(CopyOnWriteArrayList<Integer> checkPoints) {
    this.checkPoints = checkPoints;
  }

  public void setOwnRobot(int robotNumber) {
      if(robotNumber==4) {
        this.ownRobot = new Robot("hammer");
      }
      if(robotNumber==1) {
        this.ownRobot = new Robot("hulk");
      }
      if(robotNumber==2) {
        this.ownRobot = new Robot("spin");
    }
      if(robotNumber==3) {
        this.ownRobot = new Robot("squash robot");
    }
      if(robotNumber==5) {
        this.ownRobot = new Robot("Twonkey");
    }
      if(robotNumber==6) {
        this.ownRobot = new Robot("Twitch");
    }

  }


  /**
   * identify if a player is "me"
   * @param playerName
   * @return
   */
  public boolean identifyPlayer(String playerName){
    if(playerName.equals(this.getName())) {
      return true;
    }

    else return false;
   }

   public void setAI(int figure){
     this.isAI = true;
     setName(AINameArray[clientID]);
     setOwnRobot(figure);
   }
   public boolean isAI(){
    return isAI;
   }


  public CopyOnWriteArrayList<Card> getRegister() {
    return register;
  }

  public ProgrammingDeck getOwnDeck() {
    return ownDeck;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public void showHands(){
    int i = 1;
    for(Card card : hands){
      //currentGame.sendToPlayer("Card " + i + ":" + card.name,this);  //need to be modified by using protocol
      i++;
    }
  }

  /**
   * draw cards from player's own programming deck
   * @param j how many cards
   */
  @Override
  public void draw(int j) {
    for(int i = j; i > 0; i--){
      currentGame.getController().drawCardCheck(this);
      hands.add(ownDeck.getRemainingCards().get(0));
      ownDeck.getRemainingCards().remove(0);
    }
  }

  /**
   * draw damages cards from game deck
   * @param type which kind of damage
   * @param count how many
   */
  public void drawDamage(String type, int count){
    ArrayList<String> tempCards = new ArrayList<String>();
    switch (type.trim().toLowerCase()){
      case "spam":
        for(int i=0; i<count; i++){
          if(!currentGame.getController().isCardListEmpty(currentGame.gameDeck.getSpamPile())){
            Card addedCard = currentGame.gameDeck.getSpamPile().get(0);
            ownDeck.getDiscardPile().add(addedCard);
            tempCards.add(addedCard.getCardName());
          }else {
            chooseDamage(count - i);
            break;
          }
        }
        break;

      case "worm":
        for(int i=0; i<count; i++){
          if(!currentGame.getController().isCardListEmpty(currentGame.gameDeck.getWormPile())){
            Card addedCard = currentGame.gameDeck.getWormPile().get(0);
            ownDeck.getDiscardPile().add(addedCard);
            tempCards.add(addedCard.getCardName());
          }else {
            chooseDamage(count - i);
            break;
          }
        }
        break;

      case "virus":
        for(int i=0; i<count; i++){
          if(!currentGame.getController().isCardListEmpty(currentGame.gameDeck.getVirusPile())){
            Card addedCard = currentGame.gameDeck.getVirusPile().get(0);
            ownDeck.getDiscardPile().add(addedCard);
            tempCards.add(addedCard.getCardName());
          }else {
            chooseDamage(count - i);
            break;
          }
        }
        break;

      case "trojan":
        for(int i=0; i<count; i++){
          if(!currentGame.getController().isCardListEmpty(currentGame.gameDeck.getTrojanPile())){
            Card addedCard = currentGame.gameDeck.getTrojanPile().get(0);
            ownDeck.getDiscardPile().add(addedCard);
            tempCards.add(addedCard.getCardName());
          }else {
            chooseDamage(count - i);
            break;
          }
        }
        break;

      default:
        currentGame.sendMessageToAll(new ErrorMessage("unknown damage type"));
    }
    currentGame.sendMessageToAll(new DrawDamage(clientID, tempCards.toArray(new String[tempCards.size()])));
  }

  private void chooseDamage(int count){
    currentGame.sendMessageToClient(new PickDamage
        (count, currentGame.getAvailablePiles()),currentGame.getServerThreadById(clientID));
  }

  /**
   * put the remaining cards in hand to discard pile
   */
  public void discardHands(){
    CopyOnWriteArrayList<String> discardHands = new CopyOnWriteArrayList<String>();
    for(Card card : getHands()){
      getOwnDeck().getDiscardPile().add(card);
      discardHands.add(card.getCardName());
      this.hands.remove(card);
    }
    String[] discardSomeMsg = discardHands.toArray(new String[discardHands.size()]);
    currentGame.sendMessageToClient(new DiscardSome(discardSomeMsg),currentGame.getServerThreadById(clientID));
  }

  /**
   * put the remaining cards in register to discard pile
   */
  public void discardRegister(){
    for(Card card : getRegister()){
      getOwnDeck().getDiscardPile().add(card);
      this.register.remove(card);
    }
  }

  /**
   * when player's own programming deck has no remaining cards
   * shuffle the cards in the discard pile and set to remaining cards
   */
  public void shuffle(){
    ownDeck.setRemainingCards(ownDeck.getDiscardPile());
    Collections.shuffle(ownDeck.getRemainingCards());
    ownDeck.setDiscardPile(new CopyOnWriteArrayList<Card>());
    for(Card card : ownDeck.getRemainingCards()){
      card.setOwner(this);
    }
  }
}
