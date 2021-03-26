package pl.socketbyte.opengui;

import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public enum OpenGUI implements Listener {
    INSTANCE;

    private Map<Integer, GUIExtender> guiMap = new HashMap<>();

    public Map<Integer, GUIExtender> getGUIs() {
        return guiMap;
    }

    public GUIExtender getGUI(int id) {
        return guiMap.get(id);
    }


    public Map<Integer, GUIExtender> getGuiMap() {
        return guiMap;
    }

}
