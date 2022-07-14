package server;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocol.*;
import protocol.ProtocolFormat.Message;
import server.Control.DisconnectionController;
import server.Player.Player;

public class Server{

    public static final Logger logger = Logger.getLogger(Server.class.getName());
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private final ServerSocket serverSocket;

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    private static final List<ServerThread> connectedClients = new ArrayList<ServerThread>();

    public static List<ServerThread> getConnectedClients() {
        return connectedClients;
    }

    private int count = 1;
    private static Server server;

    static {
        try {
            server = new Server(1788);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Server(int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber);
    }

    public void run() {
        try {
            while (!serverSocket.isClosed()) {
                logger.info(ANSI_GREEN + "Listening for new clients. ");
                //logger.log(Level.INFO, "listening for new clients");
                //System.out.println("listening for new clients");
                Socket clientSocket = serverSocket.accept();
                clientConnectedIn(clientSocket);
                logger.info(ANSI_GREEN + "A new client connected to the server.");
                //System.out.println("new client connected");
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientConnectedIn(Socket clientSocket) throws IOException {
        ServerThread connectedClient = new ServerThread(clientSocket);
        connectedClient.setID(count);
        count ++;
        //connectedClient.setAI(false);
        connectedClient.setAlive(true);
        connectedClients.add(connectedClient);
        Thread clientThread = new Thread(connectedClient);
        clientThread.setName("ServerThread:" + connectedClient.getID());
        clientThread.start();
        sendMessageToClient(new Welcome(connectedClient.getID()),connectedClient);
    }

    public void sendMessageToClient(Message msg, ServerThread client) {
        try {
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(client.getClientSocket().getOutputStream()));
            String messageToSend = msg.toString();
            //String messageToSend = new Gson().toJson(msg);
            write.write(messageToSend);
            write.newLine();
            write.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToAll(Message msg){
        for(ServerThread client : this.connectedClients){
            sendMessageToClient(msg, client);
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void clientDisconnected(ServerThread client) {
        logger.info(ANSI_GREEN + "Client (" + client.getID() + ") disconnected");
        disconnectClient(client);
    }

    public void disconnectClient(ServerThread client){
        logger.info(ANSI_GREEN + "Removed client " + client.getID());
        connectedClients.remove(client.getID());
    }

    public static Server getServer() {
        return server;
    }


    public Player firstPlayerReady(){
        int readyPlayer=0;
        Player firstReadyPlayer=null;
        for(ServerThread target: getConnectedClients()) {
            if (target.isReady()) {
                readyPlayer++;
                firstReadyPlayer = target.getPlayer();
            }
        }
           if (readyPlayer==1){
               return firstReadyPlayer;
           }
           return null;
    }



    public static ServerThread getServerThreadById(int id){
        for(ServerThread target : getConnectedClients()){
            if(target.getID() == id){
                return target;
            }
        }
        return null;
    }
    public void messageHandle(){
        /*
        while(!messageHandler.getMessagesFromClient().isEmpty()){
            messageHandler.getMessagesFromClient().forEach((key,value) ->{
                switch (value.messageType){
                    case "SendChat":
                        SendChatBody body = (SendChatBody) value.messageBody;
                        int to = body.getTo();
                        String message = body.getMessage();
                        if(to < 0){  //public message
                            sendMessageToAll(new ReceivedChat(message,key,false));
                        }else {  //private message
                            sendMessageToClient(new ReceivedChat(message,key,true), getPlayerOnlineById(to));
                            //in PlayerOnline wrapMessage(), we already ensured that the receiver exist
                        }
                    default: // TODO: 2022/6/28 implement handling for other protocols
                        sendMessageToAll(value);
                }
            } );
        }

         */
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        logger.info(ANSI_GREEN + "Server is running");
        server.run();
        new DisconnectionController().start();
    }
}
