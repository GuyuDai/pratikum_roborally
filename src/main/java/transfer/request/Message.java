package transfer.request;

import com.google.gson.Gson;
import javafx.application.Platform;
import client.mainWindow.MainViewModel;
import server.ServerThread;
import transfer.PlayerOnline;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;


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
                break;
            case SERVER_MESSAGE:
                receiveTheMessage();
                break;
            case PRIVATE_MESSAGE:
                sendPrivateMessage(playerSocket);
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
        });
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

    private void receiveTheMessage() {
        if (this.getNameOfSender().equals("Server")) {
            Platform.runLater(() -> {
                MainViewModel.show(this.getMessage());
            });
        } else {
            Platform.runLater(() -> {
                MainViewModel.show(this.getNameOfSender() + ": " + this.getMessage());
            });
        }
    }
}
