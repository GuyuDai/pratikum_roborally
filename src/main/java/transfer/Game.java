package transfer;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import server.ServerThread;
import transfer.Player;
import transfer.PlayerOnline;
import transfer.request.Command;
import transfer.request.GameMessage;
import server.ServerThread;



public class Game {

    private List<PlayerOnline> alivePlayers = new ArrayList<>(); //for round
    private static List<PlayerOnline> playerInGame = new ArrayList<>(); //for game
    private static PlayerOnline currentPlayer; //player who is current in turn
    private int roundCounter;
    private boolean winning;
    private boolean roundWinning;
    private Player gameWinner;
    //private CardStack cardStack;
    private int playerIndex;
    //public CardStack getCardStack() {return cardStack;}
    public List<PlayerOnline> getActivePlayers() {return playerInGame;}
    private boolean gameRunning = false;

    public void addPlayers(PlayerOnline playerIn) {
        playerInGame.add(playerIn);
    }

    public void removePlayers(PlayerOnline playerOut) {
        for(PlayerOnline player: playerInGame) {
            if(player.getPlayer().getName().equals(playerOut.getPlayer().getName()));
            playerInGame.remove(playerOut);
        }
    }

    /**
     public Game() {
     this.roundCounter = 1;
     this.winning = false;
     this.cardStack = new CardStack();
     }

     public void gameProgress() {
     Card card;
     Player target;
     while(!winning) {
     sendToAll("-----------------------------------------");
     sendToAll("Round "+ roundCounter + " starts");
     //Set roundWinning to false
     roundWinning = false;
     //Initialize a new card stack
     cardStack = new CardStack();
     //Initialize every player's first card and clear every player's hand cards.
     for(int i = 0;i	< playerInGame.size(); i++) {
     playerInGame.get(i).getPlayer().setHandCard(0, null);
     playerInGame.get(i).getPlayer().setHandCard(1, null);
     playerInGame.get(i).getPlayer().addHandCard(cardStack, 0);
     }
     //Initialize players (set everyone to alive situation when new round starts)
     for(int i = 0;i	< playerInGame.size(); i++) {
     alivePlayers.add(playerInGame.get(i));
     }

     while(!roundWinning) {
     //Check if the player is still alive in round
     for(playerIndex = 0;playerIndex < alivePlayers.size();playerIndex++) {

     currentPlayer = alivePlayers.get(playerIndex);

     for(PlayerOnline player: alivePlayers) {
     if(!player.getPlayer().getName().equals(currentPlayer.getPlayer().getName())) {
     sendToPlayer("Waiting for "+currentPlayer.getPlayer().getName() + " ....", player.getPlayer());
     }else {
     sendToPlayer("It's your turn!", player.getPlayer());
     }
     }

     //Handmaid guards you for only one turn.
     if(currentPlayer.getPlayer().isGuarded()) {
     currentPlayer.getPlayer().setGuarded(false);
     }
     if(cardStack.getStack().size()==0||alivePlayers.size()==1) {
     roundWinning = true;
     }
     //Get a card
     currentPlayer.getPlayer().addHandCard(cardStack, 1);
     //Show the player his cards
     showCards(currentPlayer.getPlayer());
     //Choose a card to play and in some situations playing it automatically.
     if(playingCountess(currentPlayer.getPlayer())) {
     sendToPlayer(currentPlayer.getPlayer().getName() + " must play Countess.", currentPlayer.getPlayer());
     card = new Countess();
     }else {
     sendToPlayer("Choose a card to play!",currentPlayer.getPlayer());



     while(true) {
     try {


     card = Card.identifyCard(Command.getPlayerSelection().getSelection());
     //Condition 2. Player has this selected card
     if(!currentPlayer.getPlayer().checkCard(card)) {
     sendToPlayer("You don't have this card!", currentPlayer.getPlayer());
     Command.getPlayerSelection().refresh();
     }else {
     sendToPlayer("You selected " + card.getValue() + "#"+card.getName(),currentPlayer.getPlayer());
     Command.getPlayerSelection().refresh();
     break;
     }


     }catch (NullPointerException e) {
     continue;
     }
     }
     }
     //Remove the card
     currentPlayer.getPlayer().removeCard(card);

     if(!allGuarded(currentPlayer.getPlayer())) {
     //If the card need a target player or not
     if(card.isNeedTarget()==true) {
     //Select target player
     sendToPlayer("Enter your target:", currentPlayer.getPlayer());

     while(true) {
     try {
     target = findTarget(Command.getPlayerSelection().getSelection(),currentPlayer.getPlayer().getName(),card);

     if(!target.getName().isEmpty()) {
     if(target.isGuarded()) {
     sendToPlayer(target.getName()+" is being guarded by a handmaid.",currentPlayer.getPlayer());
     sendToPlayer("Enter your target again:",currentPlayer.getPlayer());
     Command.getPlayerSelection().refresh();
     }else {
     Command.getPlayerSelection().refresh();
     break;
     }

     }else{
     sendToPlayer("Choose a valid target!",currentPlayer.getPlayer());
     sendToPlayer("Enter your target again:",currentPlayer.getPlayer());
     Command.getPlayerSelection().refresh();
     }
     }catch (NullPointerException e) {
     continue;
     }
     }
     card.cardFunction(currentPlayer.getPlayer(), target, cardStack);
     }else {
     card.cardFunction(currentPlayer.getPlayer(), null, cardStack);
     }
     }else {
     if(card.isNeedTarget()) {
     sendToPlayer("The other players are all being guarded!",currentPlayer.getPlayer());
     }
     }

     if(cardStack.getStack().size()==0||alivePlayers.size()==1) {
     roundWinning = true;
     }
     //If a player was eliminated in this round, remove it from alivePlayers
     for (int j=0;j<alivePlayers.size();j++) {
     checkAlive(alivePlayers.get(j));
     }
     if(cardStack.getStack().size()==0||alivePlayers.size()==1) {
     roundWinning = true;
     }
     //Show alive player's left cards
     if(currentPlayer.getPlayer().isInGame()) {showCard(currentPlayer.getPlayer());}
     }
     }

     sendToAll("Round "+ roundCounter + " ends");
     //Used to test
     for(int i=0;i<alivePlayers.size();i++) {
     showCard(alivePlayers.get(i).getPlayer());
     }
     //The round winner gets a heart
     findRoundWinner().setHearts();
     //Check if the game ends after this round
     checkGameWinner();
     //Show the score
     score();
     roundCounter++;
     }
     }


     public void score() {
     for(PlayerOnline player: alivePlayers) {
     sendToPlayer(player.getPlayer().getName() + " has " + player.getPlayer().getHearts() + " hearts", player.getPlayer());
     }
     }
     public void showCards(Player player) {
     sendToPlayer("You have " + player.getHandCards()[0].getValue()
     + "#" + player.getHandCards()[0].getName() + " and " + player.getHandCards()[1].getValue() + "#" + player.getHandCards()[1].getName()
     ,player);
     }

     public void showCard(Player player) {
     sendToPlayer("You have " + player.getHandCards()[0].getValue() + "#" + player.getHandCards()[0].getName() +" now"
     ,player);
     }


     //Check if all players are being guarded

     public boolean allGuarded(Player player) {
     for(int m=0;m<alivePlayers.size();m++) {
     //Every player is guarded except the current player who is on turn.
     if(!alivePlayers.get(m).getPlayer().getName().equals(player.getName())) {
     if(!alivePlayers.get(m).getPlayer().isGuarded()) {
     return false;
     }

     }
     }
     return true;
     }


     //Check if the player must play Countess

     public boolean playingCountess(Player player) {
     int firstCard = player.getHandCard(0).getValue();
     int secondCard = player.getHandCard(1).getValue();
     // 42 = 6 * 7 Countess&King  35 = 5 * 7 Countess&Prince
     if(firstCard * secondCard == 42 || firstCard * secondCard == 35) {
     return true;
     }else {
     return false;
     }
     }

     public void result() {
     //leaderBoard();
     sendToAll("Game ends");
     sendToAll("Winner: " + gameWinner.getName());
     }


     //Check if the player is alive, if he is eliminated, remove him from alive player list.

     public void checkAlive(PlayerOnline player) {
     if(!player.getPlayer().isInGame()) {
     sendToAll(player.getPlayer().getName()+" eliminated!");
     if(playerIndex>=alivePlayers.indexOf(player)) {
     playerIndex--;
     }
     alivePlayers.remove(player);
     }
     }


     //  Check if there's a game Winner

     public void checkGameWinner() {

     // 2 Players -> 7 hearts 3 Players -> 5 hearts 4 Players -> 4 hearts
     int point = 1;

     for(int i=0;i<alivePlayers.size();i++) {
     if(alivePlayers.get(i).getPlayer().getHearts() == point) {
     winning = true;
     gameWinner = alivePlayers.get(i).getPlayer();
     result();
     }
     }
     }

     /*
     * This method returns the target Player, if the target is invalid,
     * return a new player with a blank card.

     public Player findTarget(String targetName ,String userName, Card card) {
     for(int i=0;i<alivePlayers.size();i++) {
     //When playing prince the player can select himself
     if(alivePlayers.get(i).getPlayer().getName().equals(targetName)&&
     card.getValue()==5) {
     return alivePlayers.get(i).getPlayer();
     }
     //When playing other cards the player cannot select himself
     if(alivePlayers.get(i).getPlayer().getName().equals(targetName)&&
     !alivePlayers.get(i).getPlayer().getName().equals(userName)){
     return alivePlayers.get(i).getPlayer();
     }
     }
     return new Player(null);
     }

     /*
     * This method compares every player's hand card and returns the round winner
     * who has a biggest card score.

     public Player findRoundWinner()  {
     Player currentWinner = null;
     int currentPoint = 0;
     for(int i=0;i<alivePlayers.size();i++) {
     if(alivePlayers.get(i).getPlayer().getHandCard(0).getValue() > currentPoint ) {
     currentWinner = alivePlayers.get(i).getPlayer();
     currentPoint = alivePlayers.get(i).getPlayer().getHandCard(0).getValue();
     //Handle same hand card values
     }else if(alivePlayers.get(i).getPlayer().getHandCard(0).getValue() == currentPoint ) {
     int currentScore = 0;
     int winnerScore = 0;
     for(Card card : alivePlayers.get(i).getPlayer().getDiscardedCards()) {
     currentScore += card.getValue();
     }
     for(Card card : currentWinner.getDiscardedCards()) {
     winnerScore += card.getValue();
     }
     if(currentScore > winnerScore) {
     currentWinner = alivePlayers.get(i).getPlayer();
     currentPoint = alivePlayers.get(i).getPlayer().getHandCard(0).getValue();
     }
     }
     }
     sendToAll("Round winner is "+ currentWinner.getName());
     return currentWinner;
     }


     private void sendToAll(String message) {
     GameMessage gameMessage = new GameMessage(message);
     gameMessage.sendToAll();
     }

     public static void sendToPlayer(String message, Player player){
     GameMessage gameMessage = new GameMessage(message);
     gameMessage.sendToPlayer(player);
     }

     public boolean joinGame(Player player){
     for(PlayerOnline playerInList : playerInGame){
     if(playerInList.identifyPlayer(player.getName())){
     return false;
     }
     }
     for(PlayerOnline playerOnline : ServerThread.getPlayersOnline()){
     if(playerOnline.identifyPlayer(player.getName())){
     playerInGame.add(playerOnline);
     return true;
     }
     }
     return false;
     }

     public boolean leaveGame(Player player){
     PlayerOnline removePlayer = null;
     for(PlayerOnline playerInList : playerInGame){
     if(playerInList.identifyPlayer(player.getName())){
     removePlayer = playerInList;
     }
     }
     if(removePlayer != null){
     playerInGame.remove(removePlayer);
     return true;
     } else {
     return false;
     }
     }

     public void setGameRunning(boolean gameRunning){this.gameRunning = gameRunning;}

     public boolean isGameRunning(){return gameRunning;}

     public List<PlayerOnline> getPlayerInGame() {
     return playerInGame;
     }
     **/
}
