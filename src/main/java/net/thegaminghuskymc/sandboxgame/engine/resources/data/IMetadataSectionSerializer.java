package net.thegaminghuskymc.sandboxgame.engine.resources.data;

import com.google.gson.JsonDeserializer;
import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;

@SideOnly(Side.CLIENT)
public interface IMetadataSectionSerializer<T extends IMetadataSection> extends JsonDeserializer<T>
{
    /**
     * The name of this section type as it appears in JSON.
     */
    String getSectionName();
}