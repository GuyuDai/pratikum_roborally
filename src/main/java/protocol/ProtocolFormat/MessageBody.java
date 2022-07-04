package protocol.ProtocolFormat;

import com.google.gson.Gson;
import server.Player.Player;

import java.util.List;

public class MessageBody {

    /*
    boolean isAI;

    public boolean getReady() {
        return ready;
    }

    boolean ready;

    public boolean isReady() {
        return ready;
    }

    boolean messageIsPrivate;
    boolean connected;

    int to;


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    int x;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    int y;

    int id;
    int figure;
    int  phase;
    int position;
    String[] cardsInHand;
    int register;
    int count;
    int number;
    int cardsInPile;
    int from;

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    int clientID;

    String protocol;
    String group;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;
    String message;
    String error;
    String Action;
    String name;
    String card;
    String direction;

    public int[] getClientIDs() {
        return clientIDs;
    }

    public void setClientIDs(int[] clientIDs) {
        this.clientIDs = clientIDs;
    }

    int[] clientIDs;

    public String getNewCard() {
        return newCard;
    }

    public void setNewCard(String newCard) {
        this.newCard = newCard;
    }

    String newCard;

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    String rotation;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    String source;

    int[] playerIDs;
    String[] cards;
    String[] availableMaps;
    String[] selectedMap;

    List<ActiveCard> activeCards;

    Player player;
    String map;

    boolean isConnected;

    public boolean getIsConnected(){return isConnected;}
    public void setIsConnected(boolean connect){isConnected=connect;}

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }





    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean getMessageIsPrivate() {
        return messageIsPrivate;
    }

    public void setMessageIsPrivate(boolean messageIsPrivate) {
        this.messageIsPrivate = messageIsPrivate;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getFrom() {
        return from;
    }

    public void setFrom( int from) {
        this.from = from;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCard() {
        return card;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return phase;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String[] getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(String[] cards) {
        this.cardsInHand = cards;
    }

    public String[] getCards() {
        return cards;
    }

    public void setCards(String[] cards) {
        this.cards = cards;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public int[] getPlayerIDs() {
        return playerIDs;
    }

    public void setPlayerIDs(int[] playerIDs) {
        this.playerIDs = playerIDs;
    }

    public List<ActiveCard> getActiveCards() {
        return activeCards;
    }

    public void setActiveCards(List<ActiveCard> activeCards) {
        this.activeCards = activeCards;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String[] getAvailableMaps() {
        return availableMaps;
    }

    public void setAvailableMaps(String[] availableMaps) {
        this.availableMaps = availableMaps;
    }

    public String[] getSelectedMap() {
        return selectedMap;
    }

    public void setSelectedMap(String[] selectedMap) {
        this.selectedMap = selectedMap;
    }

    public int getCardsInPile() {
        return cardsInPile;
    }

    public void setCardsInPile( int cardsInPile) {
        this.cardsInPile = cardsInPile;
    }

     */


    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}


