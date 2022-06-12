package server;


import java.io.*;
import java.net.Socket;
import java.util.*;

import com.google.gson.Gson;
import transfer.PlayerOnline;
//import transfer.cards.Game;
import transfer.request.RequestWrapper;

public class ServerThread implements Runnable {
    private static List<PlayerOnline> playersOnline = new ArrayList();
    private Socket clientSocket;
    private BufferedReader readInput;
    public static boolean gameActive = false;
   // private static Game game = null;

    public ServerThread(Socket clientSocket) throws IOException {
        this. clientSocket = clientSocket;
        try {
            readInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (!clientSocket.isClosed()) {
                String clientMessage = readInput.readLine();
                RequestWrapper wrappedRequest = new Gson().fromJson(clientMessage, RequestWrapper.class);
                identifyRequest(wrappedRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void identifyRequest(RequestWrapper wrappedRequest) {
        switch (wrappedRequest.getCurrentRequest()){
            case PLAYER_INITIALISATION:
                wrappedRequest.getPlayerInitialisation().handleRequest(clientSocket);
                break;
            case MESSAGE:
                wrappedRequest.getMessage().handleRequest(clientSocket);
                break;
            case ACCEPT_PLAYER:
                wrappedRequest.getAcceptPlayer().handleRequest(clientSocket);
                break;
            /**
             case COMMAND_REQUEST:
             wrappedRequest.getCommand().handleRequest(clientSocket);
             break;
             **/
        }
    }

    public BufferedReader getReadInput(){
        return readInput;
    }

    public Socket getClientSocket() {return clientSocket;}

    public static List<PlayerOnline> getPlayersOnline() {
        return playersOnline;
    }

    /**
     public static boolean createGame(){
     if(gameActive == false) {
     game = new Game();
     gameActive = true;
     return true;
     }
     else{
     return false;
     }
     }

     public static boolean isGameActive() {
     return gameActive;
     }

     public static Game getGame(){return game;}

     **/
}