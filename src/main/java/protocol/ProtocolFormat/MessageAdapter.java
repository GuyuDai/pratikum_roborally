package protocol.ProtocolFormat;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MessageAdapter implements JsonDeserializer<Message>{
    @Override
    public Message deserialize(JsonElement json, Type t, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("messageType").getAsString();
        try {
            return context.deserialize(jsonObject, Class.forName(type));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Element type '" + type + "' not found: ", e);
        }
    }

}
