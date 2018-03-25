package net.thegaminghuskymc.sandboxgame.engine.resources;

import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;
import net.thegaminghuskymc.sandboxgame.engine.resources.data.IMetadataSection;
import net.thegaminghuskymc.sandboxgame.engine.util.ResourceLocation;

import java.io.Closeable;
import java.io.InputStream;
import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public interface IResource extends Closeable
{
    ResourceLocation getResourceLocation();

    InputStream getInputStream();

    boolean hasMetadata();

    @Nullable
    <T extends IMetadataSection> T getMetadata(String sectionName);

    String getResourcePackName();
}