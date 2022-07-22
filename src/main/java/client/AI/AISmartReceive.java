package client.AI;

import client.*;
import com.google.gson.*;
import protocol.*;
import protocol.ErrorMessage.*;
import protocol.PlayerAdded.*;
import protocol.Reboot;
import protocol.ProtocolFormat.*;
import protocol.ReceivedChat.*;
import protocol.Welcome.*;
import server.BoardElement.*;
import server.BoardTypes.*;
import server.Control.*;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;

import static server.ServerThread.*;

public class AISmartReceive extends ClientReceive {

  private static final Logger logger = Logger.getLogger(AISmartReceive.class.getName());
  public static final String ANSI_GREEN = "\u001B[32m";
  private Random random = new Random();
  private int pointerForRegister = 0;
  private CopyOnWriteArrayList<Integer> availableStartingPoints = new CopyOnWriteArrayList<Integer>();
  List<Integer> usedCards = new ArrayList<>();

  private CopyOnWriteArrayList<Integer> availableFigures = new CopyOnWriteArrayList<Integer>();

  public String getCurrentFacingDirection() {
    return currentFacingDirection;
  }

  public void setCurrentFacingDirection(String currentFacingDirection) {
    this.currentFacingDirection = currentFacingDirection;
  }

  private String currentFacingDirection;
  public AISmartReceive(Socket socket) {
    super(socket);
    //initialize available starting positions
    availableStartingPoints.add(1);
    availableStartingPoints.add(2);
    availableStartingPoints.add(3);
    availableStartingPoints.add(4);
    availableStartingPoints.add(5);
    availableStartingPoints.add(6);
    //initialize available figures
    availableFigures.add(1);
    availableFigures.add(2);
    availableFigures.add(3);
    availableFigures.add(4);
    availableFigures.add(5);
    availableFigures.add(6);
  }

  public void run() {
    IdName.put("Server",-1);
    try {
      while (!socket.isClosed()) {
        String serverMessage = readInput.readLine();
        //System.out.println(serverMessage + "-----------original message");  //test
        Message message = wrapMessage(serverMessage);
        //System.out.println("--------------------------------------------------------------");  //test
        logger.info(ANSI_GREEN+ message + "wrapped message");  //test
        identifyMessage(message);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  private void identifyMessage(Message message) {
    String type = message.getMessageType();
    String body = message.getMessageBody();

    switch (type){
      case MessageType.helloClient:
        break;

      case MessageType.alive:
        sendMessage(new Alive().toString());
        break;

      case MessageType.welcome:
        //gets the ClientID from Server
        WelcomeBody welcomeBody = new Gson().fromJson(body,WelcomeBody.class);
        clientID = welcomeBody.getClientID();
        sendMessage(new HelloServer(GROUP,true,PROTOCOL,clientID).toString());

        break;

      case MessageType.playerAdded:
        PlayerAddedBody playerAddedBody = new Gson().fromJson(body,PlayerAddedBody.class);
        playerId = playerAddedBody.getClientID();
        playerName = playerAddedBody.getName();
        int tempFigure = playerAddedBody.getFigure();
        availableFigures.remove((Integer) tempFigure);
        IdList.add(playerId);
        robotNumbers.add(tempFigure);
        IdRobot.put(playerId,tempFigure);
        IdName.put(playerName,playerId);
        if(playerId == clientID){
          figure = tempFigure;
          aiReady();
        }
        break;

      case MessageType.receivedChat:
        ReceivedChatBody receivedChatBody = new Gson().fromJson(body,ReceivedChatBody.class);
        chatMsg = receivedChatBody.getMessage();
        fromId = receivedChatBody.getFrom();
        isPrivate = receivedChatBody.isPrivate();
        //receiveChat(chatMsg);  //reminder: there cause a "Toolkit not initialized" error
        if(fromId == -1){
          aiTrigger(chatMsg);
        }
        break;

      case MessageType.selectMap:
        //Chooses a random Map
        SelectMap.SelectMapBody selectMapBody = new Gson().fromJson(body,SelectMap.SelectMapBody.class);
        maps = selectMapBody.getAvailableMaps();
        aiChooseMap();
        break;

      case MessageType.playerStatus:
        PlayerStatus.PlayerStatusBody playerStatusBody = new Gson().fromJson(body, PlayerStatus.PlayerStatusBody.class);
        isReady = playerStatusBody.isReady();
        playerId = playerStatusBody.getClientID();
        readyList.add(isReady);
        IdReady.put(playerId,isReady);
        break;

      case MessageType.mapSelected:
        MapSelected.MapSelectedBody mapSelectedBody = new Gson().fromJson(body,MapSelected.MapSelectedBody.class);
        board = mapSelectedBody.getMap();
        break;

      case MessageType.yourCards:
        YourCards.YourCardsBody yourCardsBody = new Gson().fromJson(body, YourCards.YourCardsBody.class);
        cards = yourCardsBody.getCardsInHand();
        /*
        List<String> cardsInHand = List.of(yourCardsBody.getCardsInHand());
        // Save all the Cards on your own CardsPile
        for (String card : cardsInHand) {
          myNineCardsOnPile.add(card);
        }
        //The Programming schould be done if its your turn and the programming phase is set

         */
        aiSmartProgramming();
        break;

      case MessageType.cardSelected:
        CardSelected.CardSelectedBody cardSelectedBody = new Gson().fromJson(body, CardSelected.CardSelectedBody.class);
        playerId = cardSelectedBody.getClientID();
        register = cardSelectedBody.getRegister();
        isFilled = cardSelectedBody.isFilled();
        if(isFilled){
          sendMessage(new SelectionFinished(playerId).toString());
        }
        break;

      case MessageType.pickDamage:
        PickDamage.PickDamageBody pickDamageBody = new Gson().fromJson(body, PickDamage.PickDamageBody.class);
        damageDecks = pickDamageBody.getAvailablePiles();
        damageCount = pickDamageBody.getCount();
        aiPickDamage();
        break;

      case MessageType.startingPointTaken:
        //Saves all taken Startingpoints and removes them from the available Startingpoints.
        StartingPointTaken.StartingPointTakenBody startingPointTakenBody = new Gson().fromJson
            (body, StartingPointTaken.StartingPointTakenBody.class);
        int takenX = startingPointTakenBody.getX();
        int takenY = startingPointTakenBody.getY();
        playerId = startingPointTakenBody.getClientID();
        startingPositionAdd(takenX,takenY,playerId);
        //Wenn die gewünschte Position valide ist, werden alle Spieler darüber benachrichtigt.
        removeStartPointsInHashSet(takenX, takenY);
        //sendMessage(new SetStartingPoint(8,1).toString());
        logger.info(ANSI_GREEN + "already send msg");//test
        break;

      case MessageType.timerStarted:
        timerStarted = true;
        break;

      case MessageType.timerEnded:
        timerStarted = false;
        break;

      case MessageType.cardPlayed:
        CardPlayed.CardPlayedBody cardPlayedBody = new Gson().fromJson(body, CardPlayed.CardPlayedBody.class);
        cardPlayed = cardPlayedBody.getCard();
        playerId = cardPlayedBody.getClientID();
        IdCardPlayed.put(playerId,cardPlayed);
        break;

      case MessageType.cardsYouGotNow:
        CardsYouGotNow.CardYouGotNowBody cardYouGotNowBody = new Gson().fromJson(body, CardsYouGotNow.CardYouGotNowBody.class);
        filledRegister = cardYouGotNowBody.getCards();
        break;

      case MessageType.movement:
        Movement.MovementBody movementBody = new Gson().fromJson(body,Movement.MovementBody.class);
        playerId = movementBody.getClientID();
        y = movementBody.getX();
        x = movementBody.getY();
        positions = new Integer[]{x,y};
        IdPosition.put(playerId,positions);
        break;

      case MessageType.playerTurning:
        //Saves the direction of each player who faces
        PlayerTurning.PlayerTurningBody playerTurningBody = new Gson().fromJson(body, PlayerTurning.PlayerTurningBody.class);
        turnDirection = playerTurningBody.getRotation();
        aiSaveFacingDirection(turnDirection);
        break;

      case MessageType.animation:
        Animation.AnimationBody animationBody = new Gson().fromJson(body,Animation.AnimationBody.class);
        animationType = animationBody.getType();
        break;

      case MessageType.activePhase:
        //0 => Aufbauphase, 1 => Upgradephase, 2 => Programmierphase, 3 => Aktivierungsphase
        ActivePhase.ActivePhaseBody activePhaseBody = new Gson().fromJson(body,ActivePhase.ActivePhaseBody.class);
        activePhaseNumber = activePhaseBody.getPhase();
        if(activePhaseNumber == 0){
          aiChooseStartPoint();
        }
        if(activePhaseNumber == 1){
          aiUpgrade();
        }
        break;

      case MessageType.reboot:
        Reboot.RebootBody rebootBody = new Gson().fromJson(body, Reboot.RebootBody.class);
        rebootClientId = rebootBody.getClientID();
        if(rebootClientId == clientID){
          aiReboot();
        }
        break;

      case MessageType.currentPlayer:
        sendMessage(new PlayCard(cards[pointerForRegister]).toString());
        //Um ein Register für die aktuelle Runde auszuwählen, schickt der Client folgende Nachricht.
        sendMessage(new ChooseRegister(pointerForRegister).toString());
        pointerForRegister++;
        if(pointerForRegister == 5){
          pointerForRegister = 0;
        }
        break;

      case MessageType.energy:
        Energy.EnergyBody energyBody = new Gson().fromJson(body, Energy.EnergyBody.class);
        int supposedClient = energyBody.getClientID();
        int amount = energyBody.getCount();
        //If its the AI the energy will be added to your storage
        if (supposedClient == clientID) {
          energyStorage = energyStorage + amount;
        }
        break;

      case MessageType.checkpointReached:
        //Saves the number of Checkpoints reached
        CheckPointReached.CheckPointReachedBody checkPointReachedBody = new Gson().fromJson(body, CheckPointReached.CheckPointReachedBody.class);
        int clientIDCheckReached= checkPointReachedBody.getClientID();
        int numberOfCheckpointsReached= checkPointReachedBody.getNumber();
        //Sets the number of checkpoints reached
        if(clientIDCheckReached==clientID){
          checkPointNumber=numberOfCheckpointsReached;
        }
        break;

      case MessageType.error:
        ErrorMessageBody errorMessageBody = new Gson().fromJson(body,ErrorMessageBody.class);
        String serverMsg = errorMessageBody.getError();
        aiTrigger(serverMsg);
        break;

      case MessageType.boink:
        //kind of upgrade Card
        break;

      case MessageType.checkPointMoved:
        //If a checkpoint moves its position
        CheckPointMoved.CheckPointMovedBody checkPointMovedBody= new Gson().fromJson(body, CheckPointMoved.CheckPointMovedBody.class);
        int checkPointIDMoved = checkPointMovedBody.getCheckPointID();
        int newXPosition = checkPointMovedBody.getX();
        int newYPosition = checkPointMovedBody.getY();
        setCheckPointXPosition(newXPosition);
        setCheckPointYPosition(newYPosition);
        break;


      case MessageType.registerChosen:
        //Der Server quittiert die Auswahl und schickt diese zur Information an alle Clients.
        RegisterChosen.RegisterChosenBody registerChosenBody= new Gson().fromJson(body, RegisterChosen.RegisterChosenBody.class);
        int clientID = registerChosenBody.getClientID();
        int register = registerChosenBody.getRegister();

        break;


    }
  }

  private void aiChooseRobot(){
    int figureIndex = random.nextInt(availableFigures.size() - 1);
    figure = availableFigures.get(figureIndex);
    String name = "AI_" + clientID;
    sendMessage(new PlayerValues(name,figure).toString());
  }

  private void aiReady(){
    isReady = true;
    try {
      sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    sendMessage(new SetStatus(true).toString());
  }

  private void aiChooseMap(){
    int mapIndex = random.nextInt(maps.length - 1);
    sendMessage(new MapSelected(maps[mapIndex]).toString());
  }

  /**
   * @author: Nik
   * @param turndirection saves the facing direction of the robot accordingly
   */
  private void aiSaveFacingDirection(String turndirection){
    if (getCurrentFacingDirection().equals("UP") & turndirection=="clockwise"){
      setCurrentFacingDirection("RIGHT");
    } else if(getCurrentFacingDirection().equals("RIGHT") & turndirection=="clockwise") {
      setCurrentFacingDirection("DOWN");
    } else if (getCurrentFacingDirection().equals("DOWN") & turndirection=="clockwise") {
      setCurrentFacingDirection("LEFT");
    } else if (getCurrentFacingDirection().equals("LEFT") & turndirection=="clockwise") {
      setCurrentFacingDirection("UP");
    } else if (getCurrentFacingDirection().equals("UP") & turndirection=="counterclockwise") {
      setCurrentFacingDirection("LEFT");
    } else if (getCurrentFacingDirection().equals("RIGHT") & turndirection=="counterclockwise") {
      setCurrentFacingDirection("UP");
    } else if (getCurrentFacingDirection().equals("DOWN") & turndirection=="counterclockwise") {
      setCurrentFacingDirection("RIGHT");
    } else if (getCurrentFacingDirection().equals("LEFT") & turndirection=="counterclockwise") {
      setCurrentFacingDirection("DOWN");
    }
  }
  private void aiChooseStartPoint(){
    try {
      sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    for(Integer i : startNumbers){
      availableStartingPoints.remove(i);
    }
    int startPointNum = availableStartingPoints.get(random.nextInt(availableStartingPoints.size() - 1));
    if(board.equals("DeathTrap")){
      if(startPointNum == 1){
        sendMessage(new SetStartingPoint(1,11).toString());
      }
      if(startPointNum == 2){
        sendMessage(new SetStartingPoint(3,12).toString());
      }
      if(startPointNum == 3){
        sendMessage(new SetStartingPoint(4,11).toString());
      }
      if(startPointNum == 4){
        sendMessage(new SetStartingPoint(5,11).toString());
      }
      if(startPointNum == 5){
        sendMessage(new SetStartingPoint(6,12).toString());
      }
      if(startPointNum == 6){
        sendMessage(new SetStartingPoint(8,11).toString());
      }
    }else {
      if(startPointNum == 1){
        sendMessage(new SetStartingPoint(1,1).toString());
      }
      if(startPointNum == 2){
        sendMessage(new SetStartingPoint(3,0).toString());
      }
      if(startPointNum == 3){
        sendMessage(new SetStartingPoint(4,1).toString());
      }
      if(startPointNum == 4){
        sendMessage(new SetStartingPoint(5,1).toString());
      }
      if(startPointNum == 5){
        sendMessage(new SetStartingPoint(6,0).toString());
      }
      if(startPointNum == 6){
        sendMessage(new SetStartingPoint(8,1).toString());
      }
    }
  }

  /**
   * @author: Nik
   * sets the Checkpoints based on the maps accordingly
   * is used for the AI to know where the next checkpoint that should be reached is located
   */
  Map checkPointPositions = new HashMap<Integer, int[]>();
  public void setCheckpoints(){
    if(board.equals("DizzyHighway")){
      int [] pos= {3,12};
      checkPointPositions.put(1, pos);
    } else if (board.equals("DeathTrap")) {
      int [] pos4= {2,8};
      checkPointPositions.put(4,pos4);
      int [] pos1= {7,1};
      checkPointPositions.put(1,pos1);
      checkPointPositions.put(3,new int[]{8,7});
      checkPointPositions.put(2,new int[]{4,4});
    } else if (board.equals("ExtraCrispy")){
      checkPointPositions.put(1,new int[]{2,10});
      checkPointPositions.put(2,new int[]{7,5});
      checkPointPositions.put(3,new int[]{7,10});
      checkPointPositions.put(4,new int[]{2,5});
    } else if (board.equals("LostBearings")){
      checkPointPositions.put(1, new int[]{4,11});
      checkPointPositions.put(2, new int[]{5,4});
      checkPointPositions.put(3, new int[]{2,8});
      checkPointPositions.put(4, new int[]{7,8});
    } else if (board.equals("Twister")){
      checkPointPositions.put(1, new int[]{1,10});
      checkPointPositions.put(2, new int[]{7,6});
      checkPointPositions.put(3, new int[]{3,5});
      checkPointPositions.put(3, new int[]{7,9});

    }
  }



  /**
   * @author: Nik
   * Go to nearest Checkpoint in the right order
   *
   */
  private void aiSmartProgramming() {
    int nextCheckPoint = getCheckPointNumber()+1;
    int[] posOfNextCheckPoint = (int[]) checkPointPositions.get(nextCheckPoint);
    int nextCheckPointXPosition= posOfNextCheckPoint[0];
    int nextCheckPointYPosition= posOfNextCheckPoint[1];

    //Idea: Identify the direction towards the Checkpoint and move there straight
    //If Checkpoint is on the upper side
    try {
      sleep(6000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if(nextCheckPointXPosition<x){
      //Direction Controller
      pitCheck();
      if(currentFacingDirection=="UP"){
        //move upwards

        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)) {
            if (cards[i].equals("MoveOne")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveTwo")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveThree")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
          if (register < 5) {
            sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
            register++;
          } //reset attributions
          cards = null;
          register = 0;
        } usedCards.clear();
      } else if (currentFacingDirection=="RIGHT") {
        //try to make left turn first and then move up
        //First register
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)) {
          if (cards[i].equals("TurnLeft")) {
            sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
            register++;
            usedCards.add(i);
          }  }}
        //next registers
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)){
            if (cards[i].equals("MoveOne")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveTwo")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveThree")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
          if (register < 5) {
            sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
            register++;
          } //reset attributions
          cards = null;
          register = 0;
        } usedCards.clear();


      } else if (currentFacingDirection=="LEFT"){
        //try to make right turn first and then move up
        //First register
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)) {
            if (cards[i].equals("TurnRight")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }  }}
        //next registers
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)){
            if (cards[i].equals("MoveOne")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveTwo")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveThree")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
          if (register < 5) {
            sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
            register++;
          } //reset attributions
          cards = null;
          register = 0;
        } usedCards.clear();
      } else if (currentFacingDirection=="DOWN"){
        //try to make U turn and then move up
        //First register
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)) {
            if (cards[i].equals("TurnLeft")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
        }
        //Second register
        for (int i=0; i<9;i++){
          if (!usedCards.contains(i)){
            if (cards[i].equals("TurnLeft")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
          }
        }//oder UTurn
          if(!usedCards.get(0).equals("TurnLeft")){
            if (cards[i].equals("UTurn")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
        }
        //next registers fill with move cards
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)){
            if (cards[i].equals("MoveOne")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveTwo")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveThree")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
          if (register < 5) {
            sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
            register++;
          } //reset attributions
          cards = null;
          register = 0;
        } usedCards.clear();
      }
      //If Checkpoint is on the lower part of the board compared to your Position
    } else if(nextCheckPointXPosition>x){
      pitCheck();
      if(currentFacingDirection=="UP"){
        //try Uturn and then move down
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)) {
            if (cards[i].equals("TurnLeft")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
        }
        //Second register
        for (int i=0; i<9;i++){
          if (!usedCards.contains(i)){
            if (cards[i].equals("TurnLeft")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }//oder UTurn
          if(!usedCards.get(0).equals("TurnLeft")){
            if (cards[i].equals("UTurn")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
        }
        //next registers fill with move cards
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)){
            if (cards[i].equals("MoveOne")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveTwo")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveThree")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
          if (register < 5) {
            sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
            register++;
          } //reset attributions
          cards = null;
          register = 0;
        } usedCards.clear();

      } else if (currentFacingDirection=="RIGHT") {
        //try to make right turn first and then move down
        //First register
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)) {
            if (cards[i].equals("TurnRight")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }  }}
        //next registers
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)){
            if (cards[i].equals("MoveOne")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveTwo")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveThree")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
          if (register < 5) {
            sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
            register++;
          } //reset attributions
          cards = null;
          register = 0;
        } usedCards.clear();
      } else if (currentFacingDirection=="LEFT"){
        //try to make left turn first and then move down
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)) {
            if (cards[i].equals("TurnLeft")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }  }}
        //next registers
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)){
            if (cards[i].equals("MoveOne")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveTwo")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveThree")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
          if (register < 5) {
            sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
            register++;
          } //reset attributions
          cards = null;
          register = 0;
        } usedCards.clear();
      } else if (currentFacingDirection=="DOWN"){
        //move down
        for (int i=0; i < 9; i++) {
          if (!usedCards.contains(i)) {
            if (cards[i].equals("MoveOne")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveTwo")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            } else if (cards[i].equals("MoveThree")) {
              sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
              register++;
              usedCards.add(i);
            }
          }
          if (register < 5) {
            sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
            register++;
          } //reset attributions
          cards = null;
          register = 0;
        } usedCards.clear();
      }
    }
  }

  /**
   * @author: Nik
   * Checks if there is a pit nearby and changes the facing direction of the robot accordingly if such cards are available
   * @throws NoSuchMethodException
   */
  private void pitCheck() {
    Pit pit = new Pit(currentGame);
    Board boardType;

    switch (board) {
      case "DizzyHighway":
        boardType = new DizzyHighway();
      case "DeathTrap":
        boardType = new DeathTrap();
      case "ExtraCrispy":
        boardType = new ExtraCrispy();
      case "LostBearings":
        boardType = new LostBearings();
      case "TWister":
        boardType = new Twister();


        if (boardType.getBoardElem(x + 1, y, 0).equals(pit) && currentFacingDirection.equals("DOWN")) {
          for (int i = 0; i < 9; i++) {
            if (!usedCards.contains(i)) {
              if (cards[i].equals("TurnLeft") || cards[i].equals("TurnRight") || cards[i].equals("UTurn")) {
                sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
                register++;
              }
            }
          }
        } else if (boardType.getBoardElem(x - 1, y, 0).equals(pit) && currentFacingDirection.equals("UP")) {
          for (int i = 0; i < 9; i++) {
            if (!usedCards.contains(i)) {
              if (cards[i].equals("TurnLeft") || cards[i].equals("TurnRight") || cards[i].equals("UTurn")) {
                sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
                register++;
              }
            }
          }
        } else if (boardType.getBoardElem(x, y+1, 0).equals(pit) && currentFacingDirection.equals("RIGHT")) {
          for (int i = 0; i < 9; i++) {
            if (!usedCards.contains(i)) {
              if (cards[i].equals("TurnLeft") || cards[i].equals("TurnRight") || cards[i].equals("UTurn")) {
                sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
                register++;
              }
            }
          }
        } else if (boardType.getBoardElem(x, y-1, 0).equals(pit) && currentFacingDirection.equals("LEFT")) {
          for (int i = 0; i < 9; i++) {
            if (!usedCards.contains(i)) {
              if (cards[i].equals("TurnLeft") || cards[i].equals("TurnRight") || cards[i].equals("UTurn")) {
                sendMessage(new SelectedCard(cards[i], register, getClientID()).toString());
                register++;
              }
            }
          }
        }
    }
  }

      private void aiUpgrade () {
        //maybe we will implement
      }

  /*
  private void aiProgramming(){
    try {
      sleep(6000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    for(int i = 0; i < 5; i++){
      //int cardIndex = random.nextInt(cards.length - 1);
      sendMessage(new SelectedCard(cards[i],register,getClientID()).toString());
      register++;
    }
    //reset attributions
    cards = null;
    register = 0;
  }*/
      private void aiPickDamage () {
        try {
          sleep(6000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        ArrayList<String> cardsList = new ArrayList<String>();
        while (damageCount > 0) {
          int damageIndex = random.nextInt(damageDecks.length - 1);
          cardsList.add(damageDecks[damageIndex]);
          damageCount--;
        }
        sendMessage(new SelectedDamage(cardsList.toArray(new String[0])).toString());
      }

      private void aiReboot () {
        //do nothing
      }

      private void aiTrigger (String serverMsg){
        switch (serverMsg) {
          case "Choose your robot":
          case "this robot has been taken":
            aiChooseRobot();
            break;

        }
      }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
      private HashSet<Position> availableStartingPositions = new HashSet<>();
      private HashSet<Position> availableDeathTrapStartingPositions = new HashSet<>();
      Board deathTrap = new DeathTrap();

      private List<String> myNineCardsOnPile = new ArrayList<>();
      private String[] registerCards = new String[5];

      public void programmingPhaseAI () {
        // draw from pile 9 cards automatically

        //Choose the first five cards and put in your register
        registerCards[0] = String.valueOf(myNineCardsOnPile.get(0));
        registerCards[1] = String.valueOf(myNineCardsOnPile.get(1));
        registerCards[2] = String.valueOf(myNineCardsOnPile.get(2));
        registerCards[3] = String.valueOf(myNineCardsOnPile.get(3));
        registerCards[4] = String.valueOf(myNineCardsOnPile.get(4));

        sendMessage(new SelectedCard(registerCards[0], 0, getClientID()).toString());
        sendMessage(new SelectedCard(registerCards[1], 1, getClientID()).toString());
        sendMessage(new SelectedCard(registerCards[2], 2, getClientID()).toString());
        sendMessage(new SelectedCard(registerCards[3], 3, getClientID()).toString());
        sendMessage(new SelectedCard(registerCards[4], 4, getClientID()).toString());
      }


      public void removeStartPointsInHashSet ( int x, int y){
        HashSet<Position> delete = new HashSet<>();
        if (board.equals("Death Trap")) {
          for (Position position : availableDeathTrapStartingPositions) {
            if (position.getX() == x && position.getY() == y) {
              delete.add(position);
            }
          }

        } else {
          for (Position position : availableStartingPositions) {
            if (position.getX() == x && position.getY() == y) {
              delete.add(position);
            }
          }

        }
      }

//////////////////////////////////////////////////////////////////////////////////////////////////////
      private Message wrapMessage (String input){
        if (input.contains("\"messageType\":\"ActivePhase\",\"messageBody\"")) {
          return new Gson().fromJson(input, ActivePhase.class);
        }
        if (input.contains("\"messageType\":\"Alive\",\"messageBody\"")) {
          return new Gson().fromJson(input, Alive.class);
        }
        if (input.contains("\"messageType\":\"Animation\",\"messageBody\"")) {
          return new Gson().fromJson(input, Animation.class);
        }
        if (input.contains("\"messageType\":\"CardPlayed\",\"messageBody\"")) {
          return new Gson().fromJson(input, CardPlayed.class);
        }
        if (input.contains("\"messageType\":\"CardSelected\",\"messageBody\"")) {
          return new Gson().fromJson(input, CardSelected.class);
        }
        if (input.contains("\"messageType\":\"CardsYouGotNow\",\"messageBody\"")) {
          return new Gson().fromJson(input, CardsYouGotNow.class);
        }
        if (input.contains("\"messageType\":\"CheckPointReached\",\"messageBody\"")) {
          return new Gson().fromJson(input, CheckPointReached.class);
        }
        if (input.contains("\"messageType\":\"ClientMessage\",\"messageBody\"")) {
          return new Gson().fromJson(input, ClientMessage.class);
        }
        if (input.contains("\"messageType\":\"ConnectionUpdate\",\"messageBody\"")) {
          return new Gson().fromJson(input, ConnectionUpdate.class);
        }
        if (input.contains("\"messageType\":\"CurrentCards\",\"messageBody\"")) {
          return new Gson().fromJson(input, CurrentCards.class);
        }
        if (input.contains("\"messageType\":\"CurrentPlayer\",\"messageBody\"")) {
          return new Gson().fromJson(input, CurrentPlayer.class);
        }
        if (input.contains("\"messageType\":\"DrawDamage\",\"messageBody\"")) {
          return new Gson().fromJson(input, DrawDamage.class);
        }
        if (input.contains("\"messageType\":\"Energy\",\"messageBody\"")) {
          return new Gson().fromJson(input, Energy.class);
        }
        if (input.contains("\"messageType\":\"Error\",\"messageBody\"")) {
          return new Gson().fromJson(input, ErrorMessage.class);
        }
        if (input.contains("\"messageType\":\"GameFinished\",\"messageBody\"")) {
          return new Gson().fromJson(input, GameFinished.class);
        }
        if (input.contains("\"messageType\":\"GameStarted\",\"messageBody\"")) {
          return new Gson().fromJson(input, GameStarted.class);
        }
        if (input.contains("\"messageType\":\"HelloClient\",\"messageBody\"")) {
          return new Gson().fromJson(input, HelloClient.class);
        }
        if (input.contains("\"messageType\":\"HelloServer\",\"messageBody\"")) {
          return new Gson().fromJson(input, HelloServer.class);
        }
        if (input.contains("\"messageType\":\"MapSelected\",\"messageBody\"")) {
          return new Gson().fromJson(input, MapSelected.class);
        }
        if (input.contains("\"messageType\":\"Movement\",\"messageBody\"")) {
          return new Gson().fromJson(input, Movement.class);
        }
        if (input.contains("\"messageType\":\"NotYourCards\",\"messageBody\"")) {
          return new Gson().fromJson(input, NotYourCards.class);
        }
        if (input.contains("\"messageType\":\"PickDamage\",\"messageBody\"")) {
          return new Gson().fromJson(input, PickDamage.class);
        }
        if (input.contains("\"messageType\":\"PlayCard\",\"messageBody\"")) {
          return new Gson().fromJson(input, PlayCard.class);
        }
        if (input.contains("\"messageType\":\"PlayerAdded\",\"messageBody\"")) {
          return new Gson().fromJson(input, PlayerAdded.class);
        }
        if (input.contains("\"messageType\":\"PlayerStatus\",\"messageBody\"")) {
          return new Gson().fromJson(input, PlayerStatus.class);
        }
        if (input.contains("\"messageType\":\"PlayerTurning\",\"messageBody\"")) {
          return new Gson().fromJson(input, PlayerTurning.class);
        }
        if (input.contains("\"messageType\":\"PlayerValues\",\"messageBody\"")) {
          return new Gson().fromJson(input, PlayerValues.class);
        }
        if (input.contains("\"messageType\":\"Reboot\",\"messageBody\"")) {
          return new Gson().fromJson(input, Reboot.class);
        }
        if (input.contains("\"messageType\":\"RebootDirection\",\"messageBody\"")) {
          return new Gson().fromJson(input, RebootDirection.class);
        }
        if (input.contains("\"messageType\":\"ReceivedChat\",\"messageBody\"")) {
          return new Gson().fromJson(input, ReceivedChat.class);
        }
        if (input.contains("\"messageType\":\"ReplaceCard\",\"messageBody\"")) {
          return new Gson().fromJson(input, ReplaceCard.class);
        }
        if (input.contains("\"messageType\":\"SelectedCard\",\"messageBody\"")) {
          return new Gson().fromJson(input, SelectedCard.class);
        }
        if (input.contains("\"messageType\":\"SelectedDamage\",\"messageBody\"")) {
          return new Gson().fromJson(input, SelectedDamage.class);
        }
        if (input.contains("\"messageType\":\"SelectionFinished\",\"messageBody\"")) {
          return new Gson().fromJson(input, SelectionFinished.class);
        }
        if (input.contains("\"messageType\":\"SelectMap\",\"messageBody\"")) {
          return new Gson().fromJson(input, SelectMap.class);
        }
        if (input.contains("\"messageType\":\"SendChat\",\"messageBody\"")) {
          return new Gson().fromJson(input, SendChat.class);
        }
        if (input.contains("\"messageType\":\"SetStartingPoint\",\"messageBody\"")) {
          return new Gson().fromJson(input, SetStartingPoint.class);
        }
        if (input.contains("\"messageType\":\"SetStatus\",\"messageBody\"")) {
          return new Gson().fromJson(input, SetStatus.class);
        }
        if (input.contains("\"messageType\":\"ShuffleCoding\",\"messageBody\"")) {
          return new Gson().fromJson(input, ShuffleCoding.class);
        }
        if (input.contains("\"messageType\":\"StartingPointTaken\",\"messageBody\"")) {
          return new Gson().fromJson(input, StartingPointTaken.class);
        }
        if (input.contains("\"messageType\":\"TimerEnded\",\"messageBody\"")) {
          return new Gson().fromJson(input, TimerEnded.class);
        }
        if (input.contains("\"messageType\":\"TimerStarted\",\"messageBody\"")) {
          return new Gson().fromJson(input, TimerStarted.class);
        }
        if (input.contains("\"messageType\":\"Welcome\",\"messageBody\"")) {
          return new Gson().fromJson(input, Welcome.class);
        }
        if (input.contains("\"messageType\":\"Boink\",\"messageBody\"")) {
          return new Gson().fromJson(input, Boink.class);
        }
        if (input.contains("\"messageType\":\"ChooseRegister\",\"messageBody\"")) {
          return new Gson().fromJson(input, ChooseRegister.class);
        }
        if (input.contains("\"messageType\":\"RegisterChosen\",\"messageBody\"")) {
          return new Gson().fromJson(input, RegisterChosen.class);
        }
        if (input.contains("\"messageType\":\"ReturnCards\",\"messageBody\"")) {
          return new Gson().fromJson(input, ReturnCards.class);
        }
        if (input.contains("\"messageType\":\"CheckPointMoved\",\"messageBody\"")) {
          return new Gson().fromJson(input, CheckPointMoved.class);
        }
        if (input.contains("\"messageType\":\"YourCards\",\"messageBody\"")) {
          return new Gson().fromJson(input, YourCards.class);
        }

        return new ErrorMessage("Error when parsing String to Message");
      }
    }
