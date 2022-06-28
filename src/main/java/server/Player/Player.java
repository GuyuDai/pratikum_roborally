package server.Player;

import java.net.Socket;
import server.CardTypes.Card;
import server.CardTypes.Spam;
import server.Deck.GameDeck;
import server.Deck.ProgrammingDeck;
import java.util.concurrent.CopyOnWriteArrayList;
import server.Game.RR;


public class Player implements PlayerAction{
  public String name;
  private RR currentGame;
  private ProgrammingDeck ownDeck;
  public Robot ownRobot;
  private CopyOnWriteArrayList<Card> hands;
  private CopyOnWriteArrayList<Card> register;
  public GameDeck gameDeck;
  public int priority = 1;
  public int energyCubes;


  public Player(String name){
    this.name = name;
    this.energyCubes=5;
    this.ownDeck=new ProgrammingDeck();
  }

  public Robot getOwnRobot() {
    return ownRobot;
  }

  public String getName() {
    return name;
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

  public void setOwnRobot(int robotNumber) {

    if(!currentGame.getController().selectRobotCheck("hammer")){
      if(robotNumber==0) {
        this.ownRobot = new Robot("hammer");
      }
    }
    if(!currentGame.getController().selectRobotCheck("hulk")){
      if(robotNumber==1) {
        this.ownRobot = new Robot("hulk");
      }
    }
    if(!currentGame.getController().selectRobotCheck("spin")){
      if(robotNumber==2) {
        this.ownRobot = new Robot("spin");
      }
    }
    if(!currentGame.getController().selectRobotCheck("squash robot")){
      if(robotNumber==3) {
        this.ownRobot = new Robot("squash robot");
      }
    }
    if(!currentGame.getController().selectRobotCheck("Twonkey")){
      if(robotNumber==4) {
        this.ownRobot = new Robot("Twonkey");
      }
    }
    if(!currentGame.getController().selectRobotCheck("Twitch")){
      if(robotNumber==5) {
        this.ownRobot = new Robot("Twitch");
      }
    }


  }
   public boolean identifyPlayer(String playerName){
    if(playerName.equals(this.getName())) {
      return true;
    }
    else return false;
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
  @Override
  public void draw() {
    for(int i = 9; i > 0; i--){
      currentGame.getController().drawCardCheck(this);
      hands.add(ownDeck.getRemainingCards().get(0));
      ownDeck.getRemainingCards().remove(0);
    }
  }

  public void drawSpamCard(int count){
    for(int i=0;i<count;i++){
      // @dai: i think there needs to check whether the spam pile is empty
      // the method in Controller isCardListEmpty(..) can be used
      if(!currentGame.getController().isCardListEmpty(gameDeck.getSpamPile())) {
        hands.add(gameDeck.getSpamPile().get(0));
        gameDeck.getSpamPile().remove(0);
      }
    }
  }



}
