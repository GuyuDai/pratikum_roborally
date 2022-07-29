package protocol.ProtocolFormat;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author dai, djafari
 * identify which protocol a String is and seserialize
 * usage:  GsonBuilder gsonBuilder = new GsonBuilder();
 *         gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
 *         gson = gsonBuilder.create();
 */
public class MessageAdapter implements JsonDeserializer<Message>{
    @Override
    public Message deserialize(JsonElement json, Type t, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("messageType").getAsString();
        try {
            return context.deserialize(jsonObject, Class.forName(type));
        } catch (ClassNotFoundException e) {
            System.out.println("Element type '" + type + "' not found ");
            e.printStackTrace();
        }
        return null;
    }

}
