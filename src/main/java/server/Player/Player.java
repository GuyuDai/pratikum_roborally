package server.Player;

import server.CardTypes.*;
import server.Deck.*;
import server.Game.*;

import java.util.*;
import java.util.concurrent.*;


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

  private boolean isAI=false;


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

   public void setAI(boolean AI){
     this.isAI=AI;
   }
   public boolean getAI(){
    return isAI;
   }

   public void setAIRobot(){
    if(this.getAI()){
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
  public void programmingPhaseAI(){
    // draw from pile 9 cards automatically
    draw();
    //Choose the first five cards and put in your register
    register.add(getHands().get(0));
    register.add(getHands().get(1));
    register.add(getHands().get(2));
    register.add(getHands().get(3));
    register.add(getHands().get(4));
  }
  public void upgradePhaseAI(){

  }


}
