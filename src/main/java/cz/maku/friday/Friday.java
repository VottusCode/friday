package cz.maku.friday;

import com.google.common.collect.Maps;
import cz.maku.friday.bukkit.command.ExecutingCmd;
import cz.maku.friday.bukkit.command.FridayCmd;
import cz.maku.friday.bukkit.listener.BukkitEvents;
import cz.maku.friday.bukkit.listener.FridayEvents;
import cz.maku.friday.command.FridayCommand;
import cz.maku.friday.player.FridayPlayer;
import cz.maku.friday.storage.config.Config;
import cz.maku.friday.storage.sql.DataSource;
import cz.maku.friday.storage.sql.SQLStorage;
import cz.maku.friday.util.Logger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class Friday extends JavaPlugin {

    @Getter
    private static Friday plugin;
    @Getter
    private DataSource dataSource;
    @Getter
    private Logger log;
    @Getter
    private Config databaseConfig;
    @Getter
    private Config adminsConfig;
    @Getter
    @Setter
    private int onlinePlayers;
    @Getter
    private Map<String, FridayPlayer> fridayPlayers;

    @Override
    public void onEnable() {
        log = new Logger();
        plugin = this;
        init();
        initListener();
        initConfigs();
        log.log(Logger.LoggerType.SUCCESS, "Friday was enabled.", true);
        initCommand(new FridayCmd());
        initCommand(new ExecutingCmd());
    }

    @Override
    public void onDisable() {
        log.log(Logger.LoggerType.ERROR, "Friday was disabled.", true);
    }

    private void init() {
        fridayPlayers = Maps.newHashMap();
        dataSource = new DataSource(getConfig());
        databaseConfig = new Config(plugin, "database");
        adminsConfig = new Config(plugin, "admins");
        if (databaseConfig.isExists()) {
            dataSource.connect();
        } else {
            log.log(Logger.LoggerType.WARNING, "File database.yml is not created. Database can't be connected.", true);
        }
    }

    private void initListener() {
        getServer().getPluginManager().registerEvents(new BukkitEvents(), plugin);
        getServer().getPluginManager().registerEvents(new FridayEvents(), plugin);
    }

    private void initConfigs() {
        if (!databaseConfig.isExists()) {
            databaseConfig.createNewFile();
            databaseConfig.set("database.address", "127.0.0.1");
            databaseConfig.set("database.port", 3306);
            databaseConfig.set("database.name", "database");
            databaseConfig.set("database.user", "root");
            databaseConfig.set("database.password", "password");
        }
        if (!adminsConfig.isExists()) {
            adminsConfig.createNewFile();
            adminsConfig.createStringList("admins", "itIsMaku");
        }
    }

    public void initCommand(FridayCommand fc) {
        plugin.getCommand(fc.getCommandName()).setExecutor(fc);
    }
}
