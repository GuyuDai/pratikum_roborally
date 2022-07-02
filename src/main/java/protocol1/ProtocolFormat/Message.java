package protocol1.ProtocolFormat;

import com.google.gson.Gson;

public abstract class Message {

        public String messageType;
        public MessageBody messageBody;

        public String toString(){
            return new Gson().toJson(this);
        }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }
}


