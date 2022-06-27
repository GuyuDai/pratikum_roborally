package protocol;

import client.Client;

public interface ServerMessage <T>{
    void serverMessage(Client client, ActivePhase activePhase);
}
