package net.thegaminghuskymc.sandboxgame.engine.loaders.gui_packs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GuiJsonReader {

    public static void readGUIJson(String path) {
        try {
            System.out.print(new String(Files.readAllBytes(Paths.get(path + ".json")), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
