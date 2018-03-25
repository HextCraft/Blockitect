package net.thegaminghuskymc.sandboxgame.engine.resources.data;

import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;
import net.thegaminghuskymc.sandboxgame.engine.util.text.ITextComponent;

@SideOnly(Side.CLIENT)
public class PackMetadataSection implements IMetadataSection
{
    private final ITextComponent packDescription, packName, packCreator;
    private final int packFormat;

    public PackMetadataSection(ITextComponent packDescriptionIn, ITextComponent packNameIn, ITextComponent packCreatorIn, int packFormatIn)
    {
        this.packDescription = packDescriptionIn;
        this.packName = packNameIn;
        this.packCreator = packCreatorIn;
        this.packFormat = packFormatIn;
    }

    public ITextComponent getPackDescription()
    {
        return this.packDescription;
    }

    public ITextComponent getPackName()
    {
        return this.packName;
    }

    public ITextComponent getPackCreator()
    {
        return this.packCreator;
    }

    public int getPackFormat()
    {
        return this.packFormat;
    }
}