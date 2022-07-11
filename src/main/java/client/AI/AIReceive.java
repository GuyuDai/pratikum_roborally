package client.AI;

import client.ClientReceive;
import com.google.gson.Gson;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import protocol.ActivePhase;
import protocol.Alive;
import protocol.Animation;
import protocol.CardPlayed;
import protocol.CardSelected;
import protocol.CardsYouGotNow;
import protocol.HelloServer;
import protocol.MapSelected;
import protocol.Movement;
import protocol.PickDamage;
import protocol.PlayerAdded.PlayerAddedBody;
import protocol.PlayerStatus;
import protocol.PlayerTurning;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;
import protocol.Reboot;
import protocol.ReceivedChat.ReceivedChatBody;
import protocol.SelectMap;
import protocol.SelectedCard;
import protocol.SelectedDamage;
import protocol.SelectionFinished;
import protocol.SetStatus;
import protocol.StartingPointTaken;
import protocol.Welcome.WelcomeBody;
import protocol.YourCards;
import server.CardTypes.Card;

public class AIReceive extends ClientReceive {

  Random random = new Random();

  public AIReceive(Socket socket) {
    super(socket);
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
        aiProgramming();
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
        ActivePhase.ActivePhaseBody activePhaseBody = new Gson().fromJson(body,ActivePhase.ActivePhaseBody.class);
        activePhaseNumber = activePhaseBody.getPhase();
        break;

      case MessageType.reboot:
        Reboot.RebootBody rebootBody = new Gson().fromJson(body, Reboot.RebootBody.class);
        rebootClientId = rebootBody.getClientID();
        aiReboot();
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

}
