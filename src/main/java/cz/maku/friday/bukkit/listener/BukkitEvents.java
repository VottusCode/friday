package cz.maku.friday.bukkit.listener;

import cz.maku.friday.Friday;
import cz.maku.friday.bukkit.event.FridayPlayerLoadEvent;
import cz.maku.friday.bukkit.event.FridayPlayerQuitEvent;
import cz.maku.friday.player.FridayPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Friday.getPlugin().getFridayPlayers().put(e.getPlayer().getName(), new FridayPlayer(e.getPlayer().getName()));
        FridayPlayer fp = FridayPlayer.get(e.getPlayer().getName());
        //Load methods from sql in future
        FridayPlayerLoadEvent event = new FridayPlayerLoadEvent(fp);
        Bukkit.getPluginManager().callEvent(event);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        FridayPlayer fp = FridayPlayer.get(e.getPlayer().getName());
        FridayPlayerQuitEvent event = new FridayPlayerQuitEvent(fp);
        Bukkit.getPluginManager().callEvent(event);
        Friday.getPlugin().getFridayPlayers().remove(e.getPlayer().getName(), fp);
    }

}
