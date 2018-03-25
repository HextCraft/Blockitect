package net.thegaminghuskymc.sandboxgame.engine.resources;

import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;
import net.thegaminghuskymc.sandboxgame.engine.util.ResourceLocation;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@SideOnly(Side.CLIENT)
public interface IResourceManager
{
    Set<String> getResourceDomains();

    IResource getResource(ResourceLocation location) throws IOException;

    /**
     * Gets all versions of the resource identified by {@code location}. The list is ordered by resource pack priority
     * from lowest to highest.
     */
    List<IResource> getAllResources(ResourceLocation location) throws IOException;
}