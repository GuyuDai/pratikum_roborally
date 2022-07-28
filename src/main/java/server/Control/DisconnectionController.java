package server.Control;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import protocol.ConnectionUpdate;
import server.Server;
import server.ServerThread;

/**
 * @author Dai, Nassrin
 * used to check the protential disconnection from client
 * usage: when a serverThread received an Alive message, do DisconnectionController.updateAlive(this)
 */
public class DisconnectionController extends Thread{
  private static List<ServerThread> connectedClients = new ArrayList<ServerThread>();
  private static HashMap<Integer,Boolean> clientAlive = new HashMap<>();

  public DisconnectionController(){
    connectedClients = Server.getConnectedClients();
  }

  public void run(){
    while(this.isAlive()){
      resetValue();
      disconnectHandle();
    }
  }

  public static void updateAlive(ServerThread serverThread){
    if(clientAlive.containsKey(serverThread.getID())){
      clientAlive.replace(serverThread.getID(),true);
    }else {
      clientAlive.put(serverThread.getID(), true);
    }
  }

  private void resetValue(){
    Timer.countDown(10);
    clientAlive.forEach((key,value) ->{
      value = false;
    });
  }

  private void disconnectHandle(){
    clientAlive.forEach((key,value) ->{
      if(!value){
        clientAlive.remove(key);
        connectedClients.remove(Server.getServerThreadById(key));
        ConnectionUpdate message = new ConnectionUpdate(key,false,"remove");
        for(ServerThread client : connectedClients){
          BufferedWriter write = null;
          try {
            write = new BufferedWriter(new OutputStreamWriter(client.getClientSocket().getOutputStream()));
            write.write(message.toString());
            write.newLine();
            write.flush();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }
}
