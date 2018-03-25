package net.thegaminghuskymc.sandboxgame.engine.util.registry;

import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;

import java.util.Set;
import javax.annotation.Nullable;

public interface IRegistry<K, V> extends Iterable<V>
{
    @Nullable
    @SideOnly(Side.CLIENT)
    V getObject(K name);

    /**
     * Register an object on this registry.
     */
    void putObject(K key, V value);

    /**
     * Gets all the keys recognized by this registry.
     */
    @SideOnly(Side.CLIENT)
    Set<K> getKeys();
}