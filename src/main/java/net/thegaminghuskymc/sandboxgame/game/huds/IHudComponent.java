package net.thegaminghuskymc.sandboxgame.game.huds;

import net.thegaminghuskymc.sandboxgame.engine.Window;

public interface IHudComponent {

    void init(Window window) throws Exception;

    void render(Window window);

    void cleanup();

}