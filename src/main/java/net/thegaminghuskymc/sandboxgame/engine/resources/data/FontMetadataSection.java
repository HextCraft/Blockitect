package net.thegaminghuskymc.sandboxgame.engine.resources.data;

import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;

@SideOnly(Side.CLIENT)
public class FontMetadataSection implements IMetadataSection
{
    private final float[] charWidths;
    private final float[] charLefts;
    private final float[] charSpacings;

    public FontMetadataSection(float[] charWidthsIn, float[] charLeftsIn, float[] charSpacingsIn)
    {
        this.charWidths = charWidthsIn;
        this.charLefts = charLeftsIn;
        this.charSpacings = charSpacingsIn;
    }
}