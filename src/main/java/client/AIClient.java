package client;

import java.io.*;
import java.net.*;
import java.util.*;


public class AIClient  {

    private volatile Socket sockAI;
    //sending messages
    private PrintWriter sender;
    //reading messages
    private BufferedReader reader;
    private int clientID;
    public boolean isGameRunning=false;
    private boolean activationPhase= false;
    private List<String> aiCards= new ArrayList<>();
    private String[] aiRegister= new String[5];
    private int aiEnergyCubes=5;

    int phase;

    private String activePhase = null;

    private static ClientReceive clientAIReceive;
    private static AIClientReceive aiClientReceive;
    private static final int SERVER_PORT = 1788;
    private static final String SERVER_IP = "localhost";
    public static AIClientReceive getAiClientReceive() {
        return aiClientReceive;
    }

    //Starting the ActiviationPhase
    public void setActivationPhase(){this.activationPhase=true;}

    //constructor for AI
    /*public AIClient() throws IOException{

        sockAI= new Socket("AIHost", 1788);
        sender= new PrintWriter(sockAI.getOutputStream(),true);
        reader= new BufferedReader(new InputStreamReader(sockAI.getInputStream()));
        clientAIReceive = new ClientReceive(sockAI);
    }*/

    public void init() {
        try {
            //Build client with the local host
            Socket aiSocket = new Socket(SERVER_IP, SERVER_PORT);
            //Start thread for receiving message from server.
            aiClientReceive = new AIClientReceive(aiSocket);
            aiClientReceive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}

