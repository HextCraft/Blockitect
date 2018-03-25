package net.thegaminghuskymc.sandboxgame.game.huds;

import net.thegaminghuskymc.sandboxgame.engine.Window;
import net.thegaminghuskymc.sandboxgame.engine.util.HudUtils;
import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVGGL2.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class HudInventory implements IHudComponent {

    private long vg;

    private NVGColor colour;

    @Override
    public void init(Window window) throws Exception {
        this.vg = window.getOptions().antialiasing ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.vg == NULL) {
            throw new Exception("Could not init nanovg");
        }

        colour = NVGColor.create();
    }

    @Override
    public void render(Window window) {
        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);

        // Upper ribbon
        nvgBeginPath(vg);
        nvgRect(vg, 100, 100, window.getWidth() - 200, window.getHeight() - 200);
        nvgFillColor(vg, HudUtils.rgba(0x23, 0xa1, 0xf1, 255, colour));
        nvgFill(vg);

        nvgEndFrame(vg);

        // Restore state
        window.restoreState();
    }

    @Override
    public void cleanup() {
        nvgDelete(vg);
    }

}
