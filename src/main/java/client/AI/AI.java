package client.AI;

import client.*;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class AI extends Client {
  public static final Logger logger = Log.logFile("AILog");

  private static final int SERVER_PORT = 52020;

  //private static final int SERVER_PORT = 1788;
  private static final String SERVER_IP = "sep21.dbs.ifi.lmu.de";

  //private static final String SERVER_IP = "localhost";

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

  public static Logger getLogger(){
    return logger;
  }



}

