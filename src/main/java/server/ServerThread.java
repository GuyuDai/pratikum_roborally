package server;


import java.io.*;
import java.net.Socket;
import java.util.*;
import protocol.*;
import com.google.gson.Gson;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;
import server.Player.Player;
//import transfer.cards.Game;


public class ServerThread implements Runnable {
    //private static List<PlayerOnline> playersOnline = new ArrayList();
    private static final String PROTOCOL = "Version 1.0";
    private Socket clientSocket;
    private BufferedReader readInput;

    private  PrintWriter writeOutput;
    public static boolean gameActive = false;

     String group;
     int Id;
     boolean isAI;

    private Player player;
   // private static Game game = null;

    public ServerThread(Socket clientSocket) throws IOException {
        this. clientSocket = clientSocket;
        try {
            readInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writeOutput =new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Message helloClient = new HelloClient(PROTOCOL);
            String helloClientString=helloClient.toString();
            writeOutput.write(helloClientString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (!clientSocket.isClosed()) {
                String clientMessage = readInput.readLine();
                Message message = new Gson().fromJson(clientMessage, Message.class);
                identifyMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void identifyMessage(Message message) {
        String messageType=message.getMessageType();
        switch (messageType){
            case MessageType.helloServer:
                Id=message.getMessageBody().getId();
                group=message.getMessageBody().getGroup();
                isAI=message.getMessageBody().isAI();
                player.setAI(isAI);
                break;
            case MessageType.playerValues:
            case MessageType.setStatus:
            case MessageType.mapSelected:
            case MessageType.sendChat:
            case MessageType.playCard:
            case MessageType.selectCard:
            case MessageType.selectedDamage:
            case MessageType.setStartingPoint:

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



     public static boolean createGame(){
     if(gameActive == false) {
     //game = new Game();
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

    // public static Game getGame(){return game;}
}