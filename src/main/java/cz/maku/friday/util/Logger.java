package cz.maku.friday.util;

import com.google.common.collect.Lists;
import cz.maku.friday.player.FridayPlayer;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Data
public class Logger {
    public Logger() {
    }
    private List<FridayPlayer> players = new ArrayList<>();

    public void log(LoggerType loggerType, String message, boolean console) {
        if (loggerType.equals(LoggerType.ERROR)) {
            for (FridayPlayer fp : players) {
                fp.sendMessage("§c[F.R.I.D.A.Y.] " + message);
            }
            if (console) Bukkit.getLogger().severe(message);

        } else if (loggerType.equals(LoggerType.SUCCESS)) {
            for (FridayPlayer fp : players) {
                fp.sendMessage("§a[F.R.I.D.A.Y.] " + message);
            }
            if (console) Bukkit.getLogger().info(ChatColor.GREEN + message);
        } else if (loggerType.equals(LoggerType.LOADING)) {
            for (FridayPlayer fp : players) {
                fp.sendMessage("§b[F.R.I.D.A.Y.] " + message);
            }
            if (console) Bukkit.getLogger().info(ChatColor.AQUA + message);
        } else if (loggerType.equals(LoggerType.WARNING)) {
            for (FridayPlayer fp : players) {
                fp.sendMessage("§e[F.R.I.D.A.Y.] " + message);
            }
            if (console) Bukkit.getLogger().warning(message);
        }
    }

    public enum LoggerType {
        SUCCESS,
        WARNING,
        ERROR,
        LOADING;
    }

}
