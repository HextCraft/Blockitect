package net.thegaminghuskymc.sandboxgame.engine.loaders.gui_packs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import net.thegaminghuskymc.sandboxgame.engine.util.PathUtils;

import java.io.*;

public class GuiJsonWriter {

    private File gameDir;

    public static void writeGUIJson(String guiName, String[] components) {
        try {

            File fileDir = PathUtils.getGameDir();
            if(!fileDir.exists()) {
                fileDir.createNewFile();
            }

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir.getAbsolutePath() + "/assets/blockitect/gui_packs/" + guiName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("gui_name").value(guiName);
            jw.name("components");
            jw.beginObject();
            for (String component : components) {
                jw.name(component).value(component);
            }
            jw.endObject();
            jw.endObject();

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
