package net.thegaminghuskymc.sandboxgame.engine.loaders.gui_packs;

import net.thegaminghuskymc.sandboxgame.engine.util.IStringSerializable;

public enum EnumGui implements IStringSerializable {

    MAIN_MENU("main_menu", idk.main_menu_components, idk.componentDesc),
    WORLDS("worlds", idk.components, idk.componentDesc),
    CREATE_WORLD("create_world", idk.components, idk.componentDesc),
    RECREATE_WORLD("recreate_world", idk.components, idk.componentDesc),
    PAUSE_MENU("pause_menu", idk.components, idk.componentDesc),
    SETTINGS("settings", idk.components, idk.componentDesc),
    INVENTORY("inventory", idk.components, idk.componentDesc),
    CRAFTING("crafting", idk.components, idk.componentDesc);

    private String name;
    private String[] components, componentDesc;

    EnumGui(String name, String[] components, String[] componentDesc) {
        this.name = name;
        this.components = components;
        this.componentDesc = componentDesc;
    }

    @Override
    public String getName() {
        return name;
    }

    public String[] getComponents() {
        return components;
    }

    public String[] getComponentDesc() {
        return componentDesc;
    }

}
