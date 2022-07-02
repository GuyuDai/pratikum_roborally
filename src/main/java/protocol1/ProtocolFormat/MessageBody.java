package protocol1.ProtocolFormat;

import server.BoardElement.BoardElem;

import java.util.ArrayList;



public class MessageBody {

  public boolean isAI;
  public boolean ready;
  public boolean isPrivate;
  public boolean isConnected;
  public boolean filled;
  public int to;
  public int clientID;
  public int figure;
  public int phase;
  public int position;
  public int register;
  public int count;
  public int number;
  public int from;
  public int x;
  public int y;
  public int[] clientIDs;
  public String protocol;
  public String group;
  public String message;
  public String error;
  public String action;
  public String name;
  public String card;
  public String direction;
  public String map;
  public String newCard;
  public String type;
  public String rotation;
  public String source;

  public String messageBody;
  public String[] cards;
  public String[] availableMaps;

  public String[] cardsInHand;
  public ArrayList<ArrayList<ArrayList<BoardElem>>> gameMap;
  public String[] availablePiles;
  public Object activeCards;



  public boolean isAI() {
    return isAI;
  }

  public void setAI(boolean AI) {
    isAI = AI;
  }

  public boolean isReady() {
    return ready;
  }

  public void setReady(boolean ready) {
    this.ready = ready;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean aPrivate) {
    isPrivate = aPrivate;
  }

  public boolean isConnected() {
    return isConnected;
  }

  public void setConnected(boolean connected) {
    isConnected = connected;
  }

  public boolean isFilled() {
    return filled;
  }

  public void setFilled(boolean filled) {
    this.filled = filled;
  }

  public int getTo() {
    return to;
  }

  public void setTo(int to) {
    this.to = to;
  }

  public int getClientID() {
    return clientID;
  }

  public void setClientID(int clientID) {
    this.clientID = clientID;
  }

  public int getFigure() {
    return figure;
  }

  public void setFigure(int figure) {
    this.figure = figure;
  }

  public int getPhase() {
    return phase;
  }

  public void setPhase(int phase) {
    this.phase = phase;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getRegister() {
    return register;
  }

  public void setRegister(int register) {
    this.register = register;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public int getFrom() {
    return from;
  }

  public void setFrom(int from) {
    this.from = from;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int[] getClientIDs() {
    return clientIDs;
  }

  public void setClientIDs(int[] clientIDs) {
    this.clientIDs = clientIDs;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCard() {
    return card;
  }

  public void setCard(String card) {
    this.card = card;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public String getMap() {
    return map;
  }

  public void setMap(String map) {
    this.map = map;
  }

  public String getNewCard() {
    return newCard;
  }

  public void setNewCard(String newCard) {
    this.newCard = newCard;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRotation() {
    return rotation;
  }

  public void setRotation(String rotation) {
    this.rotation = rotation;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(String messageBody) {
    this.messageBody = messageBody;
  }

  public String[] getCards() {
    return cards;
  }

  public void setCards(String[] cards) {
    this.cards = cards;
  }

  public String[] getAvailableMaps() {
    return availableMaps;
  }

  public void setAvailableMaps(String[] availableMaps) {
    this.availableMaps = availableMaps;
  }

  public String[] getCardsInHand() {
    return cardsInHand;
  }

  public void setCardsInHand(String[] cardsInHand) {
    this.cardsInHand = cardsInHand;
  }

  public ArrayList<ArrayList<ArrayList<BoardElem>>> getGameMap() {
    return gameMap;
  }

  public void setGameMap(ArrayList<ArrayList<ArrayList<BoardElem>>> gameMap) {
    this.gameMap = gameMap;
  }

  public String[] getAvailablePiles() {
    return availablePiles;
  }

  public void setAvailablePiles(String[] availablePiles) {
    this.availablePiles = availablePiles;
  }

  public Object getActiveCards() {
    return activeCards;
  }

  public void setActiveCards(Object activeCards) {
    this.activeCards = activeCards;
  }
}

