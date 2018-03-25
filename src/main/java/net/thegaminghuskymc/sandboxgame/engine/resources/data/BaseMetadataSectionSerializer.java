package net.thegaminghuskymc.sandboxgame.engine.resources.data;

import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class BaseMetadataSectionSerializer<T extends IMetadataSection> implements IMetadataSectionSerializer<T>
{
}