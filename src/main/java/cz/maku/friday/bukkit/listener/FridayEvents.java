package cz.maku.friday.bukkit.listener;

import cz.maku.friday.Friday;
import cz.maku.friday.bukkit.event.FridayPlayerLoadEvent;
import cz.maku.friday.bukkit.event.FridayPlayerQuitEvent;
import cz.maku.friday.player.FridayPlayer;
import cz.maku.friday.util.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FridayEvents implements Listener {

    @EventHandler
    public void onFridayPlayerLoad(FridayPlayerLoadEvent e) {
        Friday.getPlugin().setOnlinePlayers(Friday.getPlugin().getOnlinePlayers() + 1);
    }

    @EventHandler
    public void onFridayPlayerQuit(FridayPlayerQuitEvent e) {
        Friday.getPlugin().setOnlinePlayers(Friday.getPlugin().getOnlinePlayers() - 1);
    }

}
