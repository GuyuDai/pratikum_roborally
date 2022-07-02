package transfer.request;

import client.gameWindow.GameViewModel;
import client.lobbyWindow.LobbyViewModel;
import com.google.gson.Gson;
import javafx.application.Platform;
import protocol.ReceivedChat;
import protocol.SendChat;
import server.Server;
import server.ServerThread;
import transfer.PlayerOnline;
import transfer.RequestWrapper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;

/**
 * @author Felicia Saruba
 *
 * Organizes messages for the chat, name of Sender, Receiver
 */
public class Message{
    private String nameOfSender;
    private String message;

    private String nameOfReciever;
    private MessageTypes typeOfMessage;

    /**
     * Constructor for general message
     * @param nameOfSender
     * @param message
     * @param typeOfMessage
     */
    public Message(String nameOfSender, String message, MessageTypes typeOfMessage) {
        this.nameOfSender = nameOfSender;
        this.message = message;
        this.typeOfMessage = typeOfMessage;
    }

    /**
     * Constructor for private message to other player
     * @param nameOfSender
     * @param nameOfReciever
     * @param message
     * @param typeOfMessage
     */
    public Message(String nameOfSender, String nameOfReciever, String message, MessageTypes typeOfMessage) {
        this.nameOfSender = nameOfSender;
        this.nameOfReciever = nameOfReciever;
        this.message = message;
        this.typeOfMessage = typeOfMessage;
    }

    public String getMessage() {
        return message;
    }

    public String getNameOfSender() {
        return nameOfSender;
    }

    public MessageTypes getTypeOfMessage() {
        return typeOfMessage;
    }

    public void handleRequest(Socket playerSocket){
        switch (typeOfMessage) {
            case CLIENT_MESSAGE:
                sendMessageToAll();
                Server.logger.log(Level.INFO, new SendChat(message, -1).toString());
                break;
            case SERVER_MESSAGE:
                receiveTheMessage();
                Server.logger.log(Level.INFO, new ReceivedChat(message, 42, true).toString());
                Server.logger.log(Level.INFO, new SendChat(message, 42).toString());

                break;
            case PRIVATE_MESSAGE:
                sendPrivateMessage(playerSocket);
                Server.logger.log(Level.INFO, new SendChat(message, 42).toString());
                break;
        }
    }

    public void sendPrivateMessage(Socket playerSocket) {
        boolean isPlayerOnline = false;
        for (PlayerOnline playerOnline : ServerThread.getPlayersOnline()) {
            if (playerOnline.getPlayer().getName().equals(nameOfReciever)) {
                isPlayerOnline = true;
                writeMessage(playerOnline.getPlayerSocket(), createMessage());
                break;
            }
        }
        if (isPlayerOnline) {
            nameOfSender = "My DM to " + nameOfReciever;
            writeMessage(playerSocket, createMessage());
        } else {
            nameOfSender = "Server";
            writeMessage(playerSocket, getErrorMessage("No such player found"));
        }
    }

    private String getErrorMessage(String message) {
        return new Gson().toJson(new RequestWrapper(new Message("Server", message, MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
    }

    private void sendMessageToAll() {
        String messageToAllPlayers = createMessage();
        ServerThread.getPlayersOnline().stream().forEach(playerOnline -> {
            writeMessage(playerOnline.getPlayerSocket(), messageToAllPlayers);
        }) ;
    }

    private String createMessage() {
        return new Gson().toJson(new RequestWrapper(new Message(this.getNameOfSender(), this.getMessage(), MessageTypes.SERVER_MESSAGE), RequestType.MESSAGE));
    }

    protected void writeMessage(Socket clientToWrite, String message) {
        try {
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(clientToWrite.getOutputStream()));
            write.write(message);
            write.newLine();
            write.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  differentiate between Chat in Lobby and Game and send the message
     */
    private void receiveTheMessage() {
        if (this.getNameOfSender().equals("Server")) {
            if(LobbyViewModel.getWindowName() == "Lobby") {
                Platform.runLater(() -> {
                    LobbyViewModel.show(this.getMessage());
                });
            }
            else{
                Platform.runLater(() -> {
                GameViewModel.show(this.getMessage());
                });
            }
        } else {
            if(LobbyViewModel.getWindowName() == "Lobby") {
                Platform.runLater(() -> {
                    LobbyViewModel.show(this.getNameOfSender() + ": " + this.getMessage());
                });
            }
            else{
                Platform.runLater(() -> {
                GameViewModel.show(this.getNameOfSender() + ": " + this.getMessage());
                });
            }
       }
    }
}
