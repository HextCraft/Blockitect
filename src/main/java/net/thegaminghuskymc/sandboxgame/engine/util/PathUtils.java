package net.thegaminghuskymc.sandboxgame.engine.util;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PathUtils {

    private static File gameDir;

    public static void loadGamedir() {
        String OS = (System.getProperty("os.name")).toUpperCase();
        String gamepath;
        if (OS.contains("WIN")) {
            gamepath = System.getenv("AppData");
        } else {
            gamepath = System.getProperty("user.home");
        }

        if (!gamepath.endsWith("/")) {
            gamepath = gamepath + "/";
        }
        gameDir = new File(gamepath + "Blockitect");

        Logger.getGlobal().log(Level.FINE, "Game directory is: " + gameDir.getAbsolutePath());
    }

    public static File getGameDir() {
        return gameDir;
    }

}
