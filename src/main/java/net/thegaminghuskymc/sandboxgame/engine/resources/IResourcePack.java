package net.thegaminghuskymc.sandboxgame.engine.resources;

import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;
import net.thegaminghuskymc.sandboxgame.engine.resources.data.IMetadataSection;
import net.thegaminghuskymc.sandboxgame.engine.resources.data.MetadataSerializer;
import net.thegaminghuskymc.sandboxgame.engine.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public interface IResourcePack
{
    InputStream getInputStream(ResourceLocation location) throws IOException;

    boolean resourceExists(ResourceLocation location);

    Set<String> getResourceDomains();

    @Nullable
    <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException;

    BufferedImage getPackImage() throws IOException;

    String getPackName();
}