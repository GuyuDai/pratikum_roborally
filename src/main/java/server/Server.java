package server;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import protocol.*;
import protocol.ProtocolFormat.Message;

public class Server implements Runnable{

    private final ServerSocket serverSocket;
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
                System.out.println("listening for new clients");
                Socket clientSocket = serverSocket.accept();
                clientConnectedIn(clientSocket);
                System.out.println("new client connected");
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
        connectedClient.setAI(false);
        connectedClient.setAlive(true);
        this.connectedClients.add(connectedClient);
        Thread clientThread = new Thread(connectedClient);
        clientThread.start();
        sendMessageToClient(new Welcome(connectedClient.getID()),connectedClient);
    }

    public void sendMessageToClient(Message msg, ServerThread client) {
        try {
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(client.getClientSocket().getOutputStream()));
            String messageToSend = msg.toString();
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
        System.out.println("Client (" + client.getID() + ") disconnected");
        disconnectClient(client);
    }

    public void disconnectClient(ServerThread client){
        System.out.println("Removed client " + client.getID());
        connectedClients.remove(client.getID());
    }

    public static Server getServer() {
        return server;
    }

    private ServerThread getPlayerOnlineById(int id){
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
        System.out.println("server starts");
        server.run();
    }
}
