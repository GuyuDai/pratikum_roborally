package client;

import protocol.ProtocolFormat.*;
import server.CardTypes.*;
import server.Player.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public class AIClient extends Client {

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


    //Starting the ActiviationPhase
    public void setActivationPhase(){this.activationPhase=true;}

    //constructor for AI
    public AIClient() throws IOException{

        sockAI= new Socket("AIHost", 1788);
        sender= new PrintWriter(sockAI.getOutputStream(),true);
        reader= new BufferedReader(new InputStreamReader(sockAI.getInputStream()));
        clientAIReceive = new ClientReceive(sockAI);
    }






}

