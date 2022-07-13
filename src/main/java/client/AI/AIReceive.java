package client.AI;

import client.*;
import com.google.gson.*;
import protocol.*;
import protocol.PlayerAdded.*;
import protocol.ProtocolFormat.*;
import protocol.ReceivedChat.*;
import protocol.Welcome.*;
import server.BoardTypes.*;
import server.Control.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public class AIReceive extends ClientReceive {

  private static final Logger logger = Logger.getLogger(AIReceive.class.getName());
  public static final String ANSI_GREEN = "\u001B[32m";

  Random random = new Random();
  int pointerForRegister = 0;
  private String[] registerCards = new String[5];

  public AIReceive(Socket socket) {
    super(socket);
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
        WelcomeBody welcomeBody=new Gson().fromJson(body,WelcomeBody.class);
        clientID=welcomeBody.getClientID();
        sendMessage(new HelloServer(GROUP,true,PROTOCOL,clientID).toString());
        break;

      case MessageType.playerAdded:
        PlayerAddedBody playerAddedBody = new Gson().fromJson(body,PlayerAddedBody.class);
        playerId = playerAddedBody.getClientID();
        playerName = playerAddedBody.getName();
        figure = playerAddedBody.getFigure();
        robotNumbers.add(figure);
        IdName.put(playerName,playerId);
        aiReady();
        break;

      case MessageType.receivedChat:
        ReceivedChatBody receivedChatBody = new Gson().fromJson(body,ReceivedChatBody.class);
        chatMsg = receivedChatBody.getMessage();
        fromId = receivedChatBody.getFrom();
        isPrivate = receivedChatBody.isPrivate();
        receiveChat(chatMsg);
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
        List<String> cardsInHand = List.of(yourCardsBody.getCardsInHand());
        // Save all the Cards on your own CardsPile
        for (String card : cardsInHand) {
          myNineCardsOnPile.add(card);
        }
        //The Programming schould be done if its your turn and the programming phase is set
        //aiProgramming();
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
        StartingPointTaken.StartingPointTakenBody startingPointTakenBody = new Gson().fromJson
            (body, StartingPointTaken.StartingPointTakenBody.class);
        int takenX = startingPointTakenBody.getX();
        int takenY = startingPointTakenBody.getY();
        playerId = startingPointTakenBody.getClientID();
        startingPositionAdd(takenX,takenY,playerId);
        //Wenn die gewünschte Position valide ist, werden alle Spieler darüber benachrichtigt.

        int startingPositionSetbyOtherPlayer = startingPointTakenBody.getClientID();
        int otherPlayerXPosition = startingPointTakenBody.getX();
        int otherPlayerYPosition = startingPointTakenBody.getY();

        removeStartPointsInHashSet(otherPlayerXPosition, otherPlayerYPosition);

        //sendMessage(new SetStartingPoint(8,1).toString());
        logger.info(ANSI_GREEN + "already send msg");
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
        PlayerTurning.PlayerTurningBody playerTurningBody = new Gson().fromJson(body, PlayerTurning.PlayerTurningBody.class);
        turnDirection = playerTurningBody.getRotation();
        break;

      case MessageType.animation:
        Animation.AnimationBody animationBody = new Gson().fromJson(body,Animation.AnimationBody.class);
        animationType = animationBody.getType();
        break;

      case MessageType.activePhase:
        //0 => Aufbauphase, 1 => Upgradephase, 2 => Programmierphase, 3 => Aktivierungsphase
        ActivePhase.ActivePhaseBody activePhaseBody = new Gson().fromJson(body,ActivePhase.ActivePhaseBody.class);
        activePhaseNumber = activePhaseBody.getPhase();
        break;

      case MessageType.reboot:
        Reboot.RebootBody rebootBody = new Gson().fromJson(body, Reboot.RebootBody.class);
        rebootClientId = rebootBody.getClientID();
        aiReboot();
        break;

      case MessageType.currentPlayer:
                if (activePhaseNumber==0) {
                    if (board.equals("Death Trap")) {


                        if (availableDeathTrapStartingPositions.contains(new Position(1,11, deathTrap))){
                        x = 1;
                        y = 11;
                        //benachrichtigt den Server
                        sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(3,12, deathTrap))){
                            x = 3;
                            y = 12;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(4,11, deathTrap))) {
                            x = 4;
                            y = 11;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(5,11, deathTrap))) {
                            x = 5;
                            y = 11;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(6,12, deathTrap))) {
                            x = 6;
                            y = 12;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(8,11,deathTrap))) {
                            x = 8;
                            y = 11;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        }

                    } else {
                        if(availableStartingPositions.contains(new Position(1,1,dizzyHighway))){
                        //There is the same Startpoint on every MAP exept DeathTrap
                        x = 1;
                        y = 1;
                        //benachrichtigt den Server
                        sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(3,0,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 3;
                            y = 0;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(4,1,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 4;
                            y = 1;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(5,1,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 5;
                            y = 1;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(6,0,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 6;
                            y = 0;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(8,1,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 8;
                            y = 1;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        }
                    }


                    }
                  //ActivationPhase
                  if (activePhaseNumber==3) {
                    //If its your turn it will always play the first register and deletes the card from the register after action.
                    String reg1 = registerCards[pointerForRegister];
                    //reg1.action();
                    pointerForRegister++;
                    //benachrichtigt den Server welche Karte gespielt wird
                    //auf dem Server wird die KartenAction aufgerufen
                    sendMessage(new PlayCard(reg1).toString());

                  //UpgradePhase
                } else if (activePhaseNumber==1) {
                    //upgradePhaseAI();
                  //ProgrammingPhase
                } else if (activePhaseNumber==2) {
                    programmingPhaseAI();
                }
                break;
    }
  }

  private void aiReady(){
    isReady = true;
    try {
      sleep(6000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    sendMessage(new SetStatus(true).toString());
  }

  private void aiChooseMap(){
    int mapIndex = random.nextInt(maps.length - 1);
    sendMessage(new MapSelected(maps[mapIndex]).toString());
  }

  private void aiProgramming(){
    try {
      sleep(6000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    for(int i = 0; i < 5; i++){
      int cardIndex = random.nextInt(cards.length - 1);
      sendMessage(new SelectedCard(cards[cardIndex],register,getClientID()).toString());
      register++;
    }
    //reset attributions
    cards = null;
    register = 0;
  }

  private List<String> myNineCardsOnPile = new ArrayList<>();
  public void programmingPhaseAI() {
    // draw from pile 9 cards automatically

    //Choose the first five cards and put in your register
    registerCards[0] = String.valueOf(myNineCardsOnPile.get(0));
    registerCards[1] = String.valueOf(myNineCardsOnPile.get(1));
    registerCards[2] = String.valueOf(myNineCardsOnPile.get(2));
    registerCards[3] = String.valueOf(myNineCardsOnPile.get(3));
    registerCards[4] = String.valueOf(myNineCardsOnPile.get(4));

    sendMessage(new SelectedCard(registerCards[0],0,getClientID()).toString());
    sendMessage(new SelectedCard(registerCards[1],1,getClientID()).toString());
    sendMessage(new SelectedCard(registerCards[2],2,getClientID()).toString());
    sendMessage(new SelectedCard(registerCards[3],3,getClientID()).toString());
    sendMessage(new SelectedCard(registerCards[4],4,getClientID()).toString());
  }

  private void aiPickDamage(){
    try {
      sleep(6000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    ArrayList<String> cardsList = new ArrayList<String>();
    while(damageCount > 0){
      int damageIndex = random.nextInt(damageDecks.length - 1);
      cardsList.add(damageDecks[damageIndex]);
      damageCount--;
    }
    sendMessage(new SelectedDamage(cardsList.toArray(new String[0])).toString());
  }

  private void aiReboot(){
    // TODO: 2022/7/9 after finish reboot
  }

  private HashSet<Position> availableStartingPositions = new HashSet<>();
  private HashSet<Position> availableDeathTrapStartingPositions = new HashSet<>();

  Board deathTrap= new DeathTrap();
  Board dizzyHighway = new DizzyHighway();
  public void setStartingPositions() {
    //DeathTrap
    availableDeathTrapStartingPositions.add(new Position(1, 11, deathTrap ));
    availableDeathTrapStartingPositions.add(new Position(3, 12, deathTrap));
    availableDeathTrapStartingPositions.add(new Position(4, 11, deathTrap));
    availableDeathTrapStartingPositions.add(new Position(5, 11, deathTrap));
    availableDeathTrapStartingPositions.add(new Position(6, 12, deathTrap));
    availableDeathTrapStartingPositions.add(new Position(8, 11, deathTrap));
    //DizzyHighway
    availableStartingPositions.add(new Position(1, 1,dizzyHighway ));
    availableStartingPositions.add(new Position(3, 0,dizzyHighway));
    availableStartingPositions.add(new Position(4, 1,dizzyHighway));
    availableStartingPositions.add(new Position(5, 1,dizzyHighway));
    availableStartingPositions.add(new Position(6, 0,dizzyHighway));
    availableStartingPositions.add(new Position(8, 1,dizzyHighway));


  }

  public void removeStartPointsInHashSet(int x, int y) {
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

  private Message wrapMessage(String input){
    if(input.contains("\"messageType\":\"ActivePhase\",\"messageBody\"")){
      return new Gson().fromJson(input, ActivePhase.class);
    }
    if(input.contains("\"messageType\":\"Alive\",\"messageBody\"")){
      return new Gson().fromJson(input, Alive.class);
    }
    if(input.contains("\"messageType\":\"Animation\",\"messageBody\"")){
      return new Gson().fromJson(input, Animation.class);
    }
    if(input.contains("\"messageType\":\"CardPlayed\",\"messageBody\"")){
      return new Gson().fromJson(input, CardPlayed.class);
    }
    if(input.contains("\"messageType\":\"CardSelected\",\"messageBody\"")){
      return new Gson().fromJson(input, CardSelected.class);
    }
    if(input.contains("\"messageType\":\"CardsYouGotNow\",\"messageBody\"")){
      return new Gson().fromJson(input, CardsYouGotNow.class);
    }
    if(input.contains("\"messageType\":\"CheckPointReached\",\"messageBody\"")){
      return new Gson().fromJson(input, CheckPointReached.class);
    }
    if(input.contains("\"messageType\":\"ClientMessage\",\"messageBody\"")){
      return new Gson().fromJson(input, ClientMessage.class);
    }
    if(input.contains("\"messageType\":\"ConnectionUpdate\",\"messageBody\"")){
      return new Gson().fromJson(input, ConnectionUpdate.class);
    }
    if(input.contains("\"messageType\":\"CurrentCards\",\"messageBody\"")){
      return new Gson().fromJson(input, CurrentCards.class);
    }
    if(input.contains("\"messageType\":\"CurrentPlayer\",\"messageBody\"")){
      return new Gson().fromJson(input, CurrentPlayer.class);
    }
    if(input.contains("\"messageType\":\"DrawDamage\",\"messageBody\"")){
      return new Gson().fromJson(input, DrawDamage.class);
    }
    if(input.contains("\"messageType\":\"Energy\",\"messageBody\"")){
      return new Gson().fromJson(input, Energy.class);
    }
    if(input.contains("\"messageType\":\"Error\",\"messageBody\"")){
      return new Gson().fromJson(input, ErrorMessage.class);
    }
    if(input.contains("\"messageType\":\"GameFinished\",\"messageBody\"")){
      return new Gson().fromJson(input, GameFinished.class);
    }
    if(input.contains("\"messageType\":\"GameStarted\",\"messageBody\"")){
      return new Gson().fromJson(input, GameStarted.class);
    }
    if(input.contains("\"messageType\":\"HelloClient\",\"messageBody\"")){
      return new Gson().fromJson(input, HelloClient.class);
    }
    if(input.contains("\"messageType\":\"HelloServer\",\"messageBody\"")){
      return new Gson().fromJson(input, HelloServer.class);
    }
    if(input.contains("\"messageType\":\"MapSelected\",\"messageBody\"")){
      return new Gson().fromJson(input, MapSelected.class);
    }
    if(input.contains("\"messageType\":\"Movement\",\"messageBody\"")){
      return new Gson().fromJson(input, Movement.class);
    }
    if(input.contains("\"messageType\":\"NotYourCards\",\"messageBody\"")){
      return new Gson().fromJson(input, NotYourCards.class);
    }
    if(input.contains("\"messageType\":\"PickDamage\",\"messageBody\"")){
      return new Gson().fromJson(input, PickDamage.class);
    }
    if(input.contains("\"messageType\":\"PlayCard\",\"messageBody\"")){
      return new Gson().fromJson(input, PlayCard.class);
    }
    if(input.contains("\"messageType\":\"PlayerAdded\",\"messageBody\"")){
      return new Gson().fromJson(input, PlayerAdded.class);
    }
    if(input.contains("\"messageType\":\"PlayerStatus\",\"messageBody\"")){
      return new Gson().fromJson(input, PlayerStatus.class);
    }
    if(input.contains("\"messageType\":\"PlayerTurning\",\"messageBody\"")){
      return new Gson().fromJson(input, PlayerTurning.class);
    }
    if(input.contains("\"messageType\":\"PlayerValues\",\"messageBody\"")){
      return new Gson().fromJson(input, PlayerValues.class);
    }
    if(input.contains("\"messageType\":\"Reboot\",\"messageBody\"")){
      return new Gson().fromJson(input, Reboot.class);
    }
    if(input.contains("\"messageType\":\"RebootDirection\",\"messageBody\"")){
      return new Gson().fromJson(input, RebootDirection.class);
    }
    if(input.contains("\"messageType\":\"ReceivedChat\",\"messageBody\"")){
      return new Gson().fromJson(input, ReceivedChat.class);
    }
    if(input.contains("\"messageType\":\"ReplaceCard\",\"messageBody\"")){
      return new Gson().fromJson(input, ReplaceCard.class);
    }
    if(input.contains("\"messageType\":\"SelectedCard\",\"messageBody\"")){
      return new Gson().fromJson(input, SelectedCard.class);
    }
    if(input.contains("\"messageType\":\"SelectedDamage\",\"messageBody\"")){
      return new Gson().fromJson(input, SelectedDamage.class);
    }
    if(input.contains("\"messageType\":\"SelectionFinished\",\"messageBody\"")){
      return new Gson().fromJson(input, SelectionFinished.class);
    }
    if(input.contains("\"messageType\":\"SelectMap\",\"messageBody\"")){
      return new Gson().fromJson(input, SelectMap.class);
    }
    if(input.contains("\"messageType\":\"SendChat\",\"messageBody\"")){
      return new Gson().fromJson(input, SendChat.class);
    }
    if(input.contains("\"messageType\":\"SetStartingPoint\",\"messageBody\"")){
      return new Gson().fromJson(input, SetStartingPoint.class);
    }
    if(input.contains("\"messageType\":\"SetStatus\",\"messageBody\"")){
      return new Gson().fromJson(input, SetStatus.class);
    }
    if(input.contains("\"messageType\":\"ShuffleCoding\",\"messageBody\"")){
      return new Gson().fromJson(input, ShuffleCoding.class);
    }
    if(input.contains("\"messageType\":\"StartingPointTaken\",\"messageBody\"")){
      return new Gson().fromJson(input, StartingPointTaken.class);
    }
    if(input.contains("\"messageType\":\"TimerEnded\",\"messageBody\"")){
      return new Gson().fromJson(input, TimerEnded.class);
    }
    if(input.contains("\"messageType\":\"TimerStarted\",\"messageBody\"")){
      return new Gson().fromJson(input, TimerStarted.class);
    }
    if(input.contains("\"messageType\":\"Welcome\",\"messageBody\"")){
      return new Gson().fromJson(input, Welcome.class);
    }
    if(input.contains("\"messageType\":\"YourCards\",\"messageBody\"")){
      return new Gson().fromJson(input, YourCards.class);
    }

    return new ErrorMessage("Error when parsing String to Message");
  }
}
