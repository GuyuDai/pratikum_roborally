package server.Player;

import protocol.YourCards;
import server.CardTypes.*;
import server.Deck.*;
import server.Game.*;

import java.util.*;
import java.util.concurrent.*;


public class Player implements PlayerAction{
  public String name;
  public int clientID;
  private RR currentGame;
  private ProgrammingDeck ownDeck;
  public Robot ownRobot;
  private CopyOnWriteArrayList<Card> hands;
  private CopyOnWriteArrayList<Card> register;
  public GameDeck gameDeck;
  public int priority = 1;
  public int energyCubes;
  private boolean isAI = false;
  private boolean isReady = false;


  public Player(String name, int ID){
    this.name = name;
    this.clientID = ID;
    this.energyCubes = 5;
    this.ownDeck = new ProgrammingDeck();
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


  public boolean identifyPlayer(String playerName){
    if(playerName.equals(this.getName())) {
      return true;
    }

    else return false;
   }

   public void setAI(boolean AI){
     this.isAI=AI;
     String[] AINameArray=new String[] {"AIBob","AIAlice","AIJay","AIMax","AICarl","AITom"};
     //TODO need a controller to make sure AI doesnt have same name
     Random rand= new Random();
     int number=rand.nextInt(5);
     String AIName=AINameArray[number];
     setName(AIName);
     setAIRobot();
  }
   public boolean isAI(){
    return isAI;
   }

   public void setAIRobot(){
    if(this.isAI()){
      Random rand=new Random();
      int robotNumber= rand.nextInt(5);
      setOwnRobot(robotNumber);
    }
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
  /*select AICard for Programming phase
randomize chosing cards and putting it into register
*/
  public void drawWormCard(int count){
    for(int i=0;i<count;i++){
      // @dai: i think there needs to check whether the spam pile is empty
      // the method in Controller isCardListEmpty(..) can be used
      if(!currentGame.getController().isCardListEmpty(gameDeck.getWormPile())) {
        hands.add(gameDeck.getWormPile().get(0));
        gameDeck.getWormPile().remove(0);
      }
    }
  }

  public void drawVirusCard(int count){
    for(int i=0;i<count;i++){
      // @dai: i think there needs to check whether the spam pile is empty
      // the method in Controller isCardListEmpty(..) can be used
      if(!currentGame.getController().isCardListEmpty(gameDeck.getVirusPile())) {
        hands.add(gameDeck.getVirusPile().get(0));
        gameDeck.getVirusPile().remove(0);
      }
    }
  }
  public void drawTrojanCard(int count){
    for(int i=0;i<count;i++){
      // @dai: i think there needs to check whether the spam pile is empty
      // the method in Controller isCardListEmpty(..) can be used
      if(!currentGame.getController().isCardListEmpty(gameDeck.getTrojanPile())) {
        hands.add(gameDeck.getSpamPile().get(0));
        gameDeck.getVirusPile().remove(0);
      }
    }
  }

}
