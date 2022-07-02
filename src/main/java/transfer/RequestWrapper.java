package transfer;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import transfer.Player;
import transfer.request.*;

/**
 *
 * Identifying the type of request that was given to client or server
 */
public class RequestWrapper {
    private RequestType currentRequest;

    private AcceptPlayer acceptPlayer;
    private Message message;
    private PlayerInitialisation playerInitialisation;
    private Command command;

    public RequestWrapper(RequestType currentRequest){
        this.currentRequest = currentRequest;
    }
    public RequestWrapper(AcceptPlayer acceptPlayer, RequestType currentRequest){
        this.acceptPlayer = acceptPlayer;
        this.currentRequest = currentRequest;
    }

    public RequestWrapper(Message message, RequestType currentRequest){
        this.message = message;
        this.currentRequest = currentRequest;
    }

    public RequestWrapper(PlayerInitialisation playerInitialisation, RequestType currentRequest){
        this.playerInitialisation = playerInitialisation;
        this.currentRequest = currentRequest;
    }

    public RequestWrapper(Command command, RequestType currentRequest){
        this.command = command;
        this.currentRequest = currentRequest;
    }

    public RequestType getCurrentRequest() {
        return currentRequest;
    }

    public AcceptPlayer getAcceptPlayer() {
        return acceptPlayer;
    }

    public Message getMessage() {
        return message;
    }

    public PlayerInitialisation getPlayerInitialisation() {
        return playerInitialisation;
    }

    public Command getCommand() {
        return command;
    }
}