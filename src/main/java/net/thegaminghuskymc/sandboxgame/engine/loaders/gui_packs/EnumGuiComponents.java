package net.thegaminghuskymc.sandboxgame.engine.loaders.gui_packs;

import net.thegaminghuskymc.sandboxgame.engine.util.IStringSerializable;

public enum EnumGuiComponents implements IStringSerializable {

    BUTTON("button", 0, "This is a button"),
    TEXT_INPUT("text_input", 1, "This is a text input"),
    SLOT("slot", 2, "This is a slot"),
    TEXT("text", 3, "This is some text"),
    CONSOLE("console", 4, "This is a console"),
    IMAGE("image", 5, "This is an image"),
    BAR("bar", 6, "This is a bar"),
    ITEM("item", 7, "This is an item"),
    BLOCK("block", 8, "This is a block"),
    THREEDE_RENDER("threede_render", 9, "This is a threede render");

    private String name, componentDesc;
    private int ID;

    EnumGuiComponents(String name, int id, String componentDesc) {
        this.name = name;
        this.ID = id;
        this.componentDesc = componentDesc;
    }

    public String getName() {
        return name;
    }

    public String getComponentDesc() {
        return componentDesc;
    }

    public int getID() {
        return ID;
    }

}
