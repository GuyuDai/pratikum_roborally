package protocol1.ProtocolFormat;

import server.BoardElement.BoardElem;

import java.util.ArrayList;



public class MessageBody {

  public boolean isAI;
  public boolean isAI() {return isAI;}
  public void setAI(boolean AI) {isAI = AI;}


  public boolean ready;
  public boolean isReady() {return ready;}
  public void setReady(boolean ready) {this.ready = ready;}


  public boolean isPrivate;
  public boolean isPrivate() {return isPrivate;}
  public void setIsPrivate(boolean IsPrivate) {isPrivate = IsPrivate;}


  public boolean isConnected;
  public boolean isConnected() {return isConnected;}
  public void setConnected(boolean connected) {isConnected = connected;}


  public boolean filled;
  public boolean isFilled() {return filled;}
  public void setFilled(boolean filled) {this.filled = filled;}


  public int to;
  public int getTo() {return to;}
  public void setTo(int to) {this.to = to;}


  public int clientID;
  public int getClientID() {return clientID;}
  public void setClientID(int clientID) {this.clientID = clientID;}


  public int figure;
  public int getFigure() {return figure;}
  public void setFigure(int figure) {this.figure = figure;}


  public int phase;
  public int getPhase() {return phase;}
  public void setPhase(int phase) {this.phase = phase;}


  public int position;
  public int getPosition() {return position;}
  public void setPosition(int position) {this.position = position;}


  public int register;
  public int getRegister() {return register;}
  public void setRegister(int register) {this.register = register;}


  public int count;
  public int getCount() {return count;}
  public void setCount(int count) {this.count = count;}


  public int number;
  public int getNumber() {return number;}
  public void setNumber(int number) {this.number = number;}


  public int from;
  public int getFrom() {return from;}
  public void setFrom(int from) {this.from = from;}


  public int x;
  public int getX() {return x;}
  public void setX(int x) {this.x = x;}


  public int y;
  public int getY() {return y;}
  public void setY(int y) {this.y = y;}


  public int[] clientIDs;
  public int[] getClientIDs() {return clientIDs;}
  public void setClientIDs(int[] clientIDs) {this.clientIDs = clientIDs;}


  public String protocol;
  public String getProtocol() {return protocol;}
  public void setProtocol(String protocol) {this.protocol = protocol;}



  public String group;
  public String getGroup() {return group;}
  public void setGroup(String group) {this.group = group;}


  public String message;
  public String getMessage() {return message;}
  public void setMessage(String message) {this.message = message;}


  public String error;
  public String getError() {return error;}
  public void setError(String error) {this.error = error;}


  public String action;
  public String getAction() {return action;}
  public void setAction(String action) {this.action = action;}


  public String name;
  public String getName() {return name;}
  public void setName(String name) {this.name = name;}


  public String card;
  public String getCard() {return card;}
  public void setCard(String card) {this.card = card;}


  public String direction;
  public String getDirection() {return direction;}
  public void setDirection(String direction) {this.direction = direction;}


  public String newCard;
  public String getNewCard() {return newCard;}
  public void setNewCard(String newCard) {this.newCard = newCard;}


  public String type;
  public String getType() {return type;}
  public void setType(String type) {this.type = type;}


  public String rotation;
  public String getRotation() {return rotation;}
  public void setRotation(String rotation) {this.rotation = rotation;}


  public String source;
  public String getSource() {return source;}
  public void setSource(String source) {this.source = source;}


  public String[] cards;
  public String[] getCards() {return cards;}
  public void setCards(String[] cards) {this.cards = cards;}


  public String[] availableMaps;
  public String[] getAvailableMaps() {return availableMaps;}
  public void setAvailableMaps(String[] availableMaps) {this.availableMaps = availableMaps;}


  public String[] map;
  public String[] getMap() {return map;}
  public void setMap(String[] map) {this.map = map;}


  public String[] cardsInHand;
  public String[] getCardsInHand() {return cardsInHand;}
  public void setCardsInHand(String[] cardsInHand) {this.cardsInHand = cardsInHand;}


  public String[] availablePiles;
  public String[] getAvailablePiles() {return availablePiles;}
  public void setAvailablePiles(String[] availablePiles) {this.availablePiles = availablePiles;}


  public Object activeCards;
  public Object getActiveCards() {return activeCards;}
  public void setActiveCards(Object activeCards) {this.activeCards = activeCards;}


  public ArrayList<ArrayList<ArrayList<BoardElem>>> gameMap;
  public ArrayList<ArrayList<ArrayList<BoardElem>>> getGameMap() {return gameMap;}
  public void setGameMap(ArrayList<ArrayList<ArrayList<BoardElem>>> gameMap) {this.gameMap = gameMap;}

}

