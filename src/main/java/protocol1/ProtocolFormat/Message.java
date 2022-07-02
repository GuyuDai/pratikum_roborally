package protocol1.ProtocolFormat;

import com.google.gson.Gson;

public abstract class Message {

        public String messageType;
        public MessageBody messageBody;

        public String toString(){
            return new Gson().toJson(this);
        }
    }


