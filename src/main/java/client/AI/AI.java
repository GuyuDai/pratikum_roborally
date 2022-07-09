package client.AI;

import client.Client;
import java.io.IOException;
import java.net.Socket;

public class AI extends Client {
  private static final int SERVER_PORT = 1788;
  private static final String SERVER_IP = "localhost";

  private static AIReceive aiReceive;

  public static AIReceive getAiReceive() {
    return aiReceive;
  }

  public void init() {
    try {
      //Build client with the local host
      Socket aiSocket = new Socket(SERVER_IP, SERVER_PORT);
      //Start thread for receiving message from server.
      aiReceive = new AIReceive(aiSocket);
      aiReceive.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
