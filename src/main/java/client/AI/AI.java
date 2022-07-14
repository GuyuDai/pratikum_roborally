package client.AI;

import client.*;

import java.io.*;
import java.net.*;

public class AI extends Client {
  private static final int SERVER_PORT = 1788;
  private static final String SERVER_IP = "localhost";

  private static AIReceive aiReceive;

  public static AIReceive getAiReceive() {
    return aiReceive;
  }

  //Starting AI from here
  public static void main(String[] args) throws IOException {
    AI ki = new AI();
    ki.init();
  }



  public void init() {

    try {
      //Build client with the local host
      Socket aiSocket = new Socket(SERVER_IP, SERVER_PORT);
      //Start thread for receiving message from server.
      //getClientReceive().getSocket().close();
      aiReceive = new AIReceive(aiSocket);
      aiReceive.start();
      //initialisiert alle verfügbaren Startpositionen
      //aiReceive.setStartingPositions();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}

