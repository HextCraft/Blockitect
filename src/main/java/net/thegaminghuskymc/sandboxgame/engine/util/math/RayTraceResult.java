package net.thegaminghuskymc.sandboxgame.engine.util.math;

import net.thegaminghuskymc.sandboxgame.engine.util.EnumFacing;

public class RayTraceResult
{
    /** Used to determine what sub-segment is hit */
    public int subHit = -1;

    /** Used to add extra hit info */
    public Object hitInfo = null;

    private BlockPos blockPos;
    /** The type of hit that occured, see  for possibilities. */
    public RayTraceResult.Type typeOfHit;
    public EnumFacing sideHit;
    /** The vector position of the hit */
    public Vec3d hitVec;

    public RayTraceResult(Vec3d hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn)
    {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, blockPosIn);
    }

    public RayTraceResult(Vec3d hitVecIn, EnumFacing sideHitIn)
    {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, BlockPos.ORIGIN);
    }

    public RayTraceResult(RayTraceResult.Type typeIn, Vec3d hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn)
    {
        this.typeOfHit = typeIn;
        this.blockPos = blockPosIn;
        this.sideHit = sideHitIn;
        this.hitVec = new Vec3d(hitVecIn.x, hitVecIn.y, hitVecIn.z);
    }

    public BlockPos getBlockPos()
    {
        return this.blockPos;
    }

    public String toString()
    {
        return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec  + '}';
    }

    public enum Type
    {
        MISS,
        BLOCK,
        ENTITY;
    }
}