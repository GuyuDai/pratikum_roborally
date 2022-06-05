


package transfer.request;

import com.google.gson.Gson;
import server.ServerThread;
import transfer.Player;
import transfer.PlayerOnline;
import transfer.cards.Game;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream.PutField;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Command {

    /**
     private CommandType commandType;
     private static PlayerSelection playerSelection;
     private String selection;
     private static Game game;
     private Player playerRequesting;


     public Command(CommandType commandType, Player playerRequesting, String selection){
     this.commandType = commandType;
     this.playerRequesting = playerRequesting;
     this.selection = selection;
     }


     public Command(CommandType commandType) {
     this.commandType = commandType;
     }

     public Command(CommandType commandType, Player playerRequesting) {
     this.commandType = commandType;
     this.playerRequesting = playerRequesting;
     }


     public void handleRequest(Socket playerSocket) {
     switch (commandType) {
     case CREATE_GAME:
     createGame(playerSocket);
     break;
     case JOIN_GAME:
     joinGame(playerSocket);
     break;
     case LEAVE_GAME:
     leaveGame(playerSocket);
     break;
     case START_GAME:
     startGame(playerSocket);
     break;
     case LEAVE_SERVER:
     leaveServer(playerSocket);
     break;

     case SELECT_CARD:
     playerSelection = new PlayerSelection(selection, playerSocket, playerRequesting);
     break;
     case SELECT_PLAYER:
     playerSelection = new PlayerSelection(selection, playerSocket, playerRequesting);
     break;
     }
     }

     private void createGame(Socket playerSocket) {
     boolean gameCreated = ServerThread.createGame();
     if (gameCreated) {
     String message = new Gson().toJson(new RequestWrapper(new Message("Server", "Game created.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     for (PlayerOnline playerOnline : ServerThread.getPlayersOnline()) {
     write(message, playerOnline.getPlayerSocket());
     }
     } else {
     String message = new Gson().toJson(new RequestWrapper(new Message("Server", "Game already created.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     write(message, playerSocket);
     }

     }

     public static PlayerSelection getPlayerSelection() {
     return playerSelection;
     }

     private void joinGame(Socket playerSocket) {
     String message = "";
     if (ServerThread.isGameActive()) {
     if (ServerThread.getGame().isGameRunning()) {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "You can not join the game, because a game is currently running.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     } else {
     if (ServerThread.getGame().getPlayerInGame().size() < 4) {
     boolean playerNotAlreadyInQueue = ServerThread.getGame().joinGame(playerRequesting);
     if (playerNotAlreadyInQueue) {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "You joined the game.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     } else {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "You already are listed as a player.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     }
     } else {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "Already enough people are in the game. Please try later.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     }
     }
     } else {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "There is no Game created at the moment. Please create a game with '!create game'.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     }
     write(message, playerSocket);
     }

     private void startGame(Socket playerSocket) {
     String message = "";
     if (ServerThread.isGameActive()) {
     if (ServerThread.getGame().isGameRunning()) {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "There is already a game running at this Server. Please wait until this game has been ended.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     write(message, playerSocket);
     } else {
     if (ServerThread.getGame().getPlayerInGame().size() >= 2) {
     startNewGame();
     } else {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "There are not enough player currently in this lobby.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     write(message, playerSocket);
     }
     }
     } else {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "There is no Game created at the moment. Please create a game with '!create game'.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     write(message, playerSocket);
     }
     }

     private void startNewGame() {
     ServerThread.getGame().gameProgress();
     ServerThread.getGame().setGameRunning(true);
     String message = new Gson().toJson(new RequestWrapper(new Message("Server", "", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     for (PlayerOnline playerOnline : ServerThread.getPlayersOnline()) {
     write(message, playerOnline.getPlayerSocket());
     }
     }

     private void leaveGame(Socket playerSocket) {
     String message = "";
     if (ServerThread.isGameActive()) {
     boolean leftGame = ServerThread.getGame().leaveGame(playerRequesting);
     if (leftGame) {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "You are no longer playing.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     } else {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "You are not listed as a player. Join the game with '!join'.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     }
     } else {
     message = new Gson().toJson(new RequestWrapper(new Message("Server", "There is no Game created at the moment. Please create a game with '!create game'.", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     }
     write(message, playerSocket);
     }


     private void leaveServer(Socket playerSocket) {
     String message = new Gson().toJson(new RequestWrapper(new Message("Server", playerRequesting.getName() + " left the Server", MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
     PlayerOnline removeThisInstance = null;
     for (PlayerOnline playerOnline : ServerThread.getPlayersOnline()) {
     if (!playerOnline.identifyPlayer(playerRequesting.getName())) {
     write(message, playerOnline.getPlayerSocket());
     } else {
     removeThisInstance = playerOnline;
     write(new Gson().toJson(new RequestWrapper(RequestType.CLOSE)), playerSocket);
     }
     }
     if (removeThisInstance != null) {
     ServerThread.getPlayersOnline().remove(removeThisInstance);
     }
     }

     public void write(String message, Socket thread) {
     try {
     BufferedWriter writeOutput = new BufferedWriter(new OutputStreamWriter(thread.getOutputStream()));
     writeOutput.write(message);
     writeOutput.newLine();
     writeOutput.flush();
     } catch (IOException e) {
     e.printStackTrace();
     }
     }
     **/
}

