package net.thegaminghuskymc.sandboxgame.engine.resources.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;
import net.thegaminghuskymc.sandboxgame.engine.util.JsonUtils;
import net.thegaminghuskymc.sandboxgame.engine.util.text.ITextComponent;

import java.lang.reflect.Type;

@SideOnly(Side.CLIENT)
public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer<PackMetadataSection> implements JsonSerializer<PackMetadataSection>
{
    public PackMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
    {
        JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
        ITextComponent description = p_deserialize_3_.deserialize(jsonobject.get("description"), ITextComponent.class);
        ITextComponent name = p_deserialize_3_.deserialize(jsonobject.get("name"), ITextComponent.class);
        ITextComponent creator = p_deserialize_3_.deserialize(jsonobject.get("creator"), ITextComponent.class);

        if (description == null)
        {
            throw new JsonParseException("Invalid/missing description!");
        }
        else if (name == null)
        {
            throw new JsonParseException("Invalid/missing name!");
        }
        else if (creator == null)
        {
            throw new JsonParseException("Invalid/missing creator!");
        }
        else
        {
            int i = JsonUtils.getInt(jsonobject, "pack_format");
            return new PackMetadataSection(description, name, creator, i);
        }
    }

    public JsonElement serialize(PackMetadataSection p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
    {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("pack_format", Integer.valueOf(p_serialize_1_.getPackFormat()));
        jsonobject.add("description", p_serialize_3_.serialize(p_serialize_1_.getPackDescription()));
        jsonobject.add("name", p_serialize_3_.serialize(p_serialize_1_.getPackName()));
        jsonobject.add("creator", p_serialize_3_.serialize(p_serialize_1_.getPackCreator()));
        return jsonobject;
    }

    /**
     * The name of this section type as it appears in JSON.
     */
    public String getSectionName()
    {
        return "pack";
    }
}