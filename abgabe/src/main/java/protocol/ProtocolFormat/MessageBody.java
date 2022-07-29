package protocol.ProtocolFormat;

import com.google.gson.Gson;
import server.Player.Player;

import java.util.List;

/**
 * @author Dai, Djafari
 */
public class MessageBody {


    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}


