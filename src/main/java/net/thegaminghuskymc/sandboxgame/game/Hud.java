package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.Window;
import net.thegaminghuskymc.sandboxgame.game.huds.HudInventory;
import net.thegaminghuskymc.sandboxgame.game.huds.HudTopBar;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;

public class Hud {

    private HudInventory inventory = new HudInventory();

    public void init(Window window) throws Exception {
        inventory.init(window);
    }

    public void render(Window window) {
        if(window.isKeyPressed(GLFW_KEY_E)) {
            inventory.render(window);
        }
    }

    public void cleanup() {
        inventory.cleanup();
    }

}
