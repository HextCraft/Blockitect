package net.thegaminghuskymc.sandboxgame.engine.resources.data;

import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;

@SideOnly(Side.CLIENT)
public class AnimationFrame
{
    private final int frameIndex;
    private final int frameTime;

    public AnimationFrame(int frameIndexIn)
    {
        this(frameIndexIn, -1);
    }

    public AnimationFrame(int frameIndexIn, int frameTimeIn)
    {
        this.frameIndex = frameIndexIn;
        this.frameTime = frameTimeIn;
    }

    public boolean hasNoTime()
    {
        return this.frameTime == -1;
    }

    public int getFrameTime()
    {
        return this.frameTime;
    }

    public int getFrameIndex()
    {
        return this.frameIndex;
    }
}