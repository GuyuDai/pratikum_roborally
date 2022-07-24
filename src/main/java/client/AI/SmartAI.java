package client.AI;

import client.*;

import java.io.*;
import java.net.*;

public class SmartAI extends Client {
  //private static final int SERVER_PORT = 1788;
  //private static final String SERVER_IP = "localhost";

  private static final int SERVER_PORT = 52020;
  private static final String SERVER_IP = "sep21.dbs.ifi.lmu.de";
  private static AISmartReceive aiSmartReceive;

  public static AISmartReceive getAiSmartReceive() {
    return aiSmartReceive;
  }

  //Starting AI from here
  public static void main(String[] args) throws IOException {
    SmartAI ki = new SmartAI();
    ki.init();
  }



  public void init() {

    try {
      //Build client with the local host
      Socket aiSocket = new Socket(SERVER_IP, SERVER_PORT);
      //Start thread for receiving message from server.
      //getClientReceive().getSocket().close();
      aiSmartReceive = new AISmartReceive(aiSocket);
      aiSmartReceive.start();
      //initialisiert alle verfügbaren Startpositionen
      //aiReceive.setStartingPositions();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }



}

