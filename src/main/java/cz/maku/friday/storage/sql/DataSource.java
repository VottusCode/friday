package cz.maku.friday.storage.sql;

import cz.maku.friday.Friday;
import cz.maku.friday.util.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class DataSource {

  private final FileConfiguration config;
  private final Logger log;
  public Connection con;

  public DataSource(FileConfiguration config) {
    this.config = config;
    log = Friday.getPlugin().getLog();
  }

  public void connect() {
    if (!isConnected()) {
      String address = (String) Friday
        .getPlugin()
        .getDatabaseConfig()
        .get("database.address");
      int port = (int) Friday
        .getPlugin()
        .getDatabaseConfig()
        .get("database.port");
      String name = (String) Friday
        .getPlugin()
        .getDatabaseConfig()
        .get("database.name");
      String user = (String) Friday
        .getPlugin()
        .getDatabaseConfig()
        .get("database.user");
      String password = (String) Friday
        .getPlugin()
        .getDatabaseConfig()
        .get("database.password");
      try {
        con =
          DriverManager.getConnection(
            "jdbc:mysql://" +
            address +
            ":" +
            port +
            "/" +
            name +
            "?autoReconnect=true&useSSL=false",
            user,
            password
          );
        log.log(Logger.LoggerType.SUCCESS, "Database connected.", true);
      } catch (SQLException e) {
        e.printStackTrace();
        Friday.getPlugin().getPluginLoader().disablePlugin(Friday.getPlugin());
      }
    }
  }

  public void disconnect() {
    if (isConnected()) {
      try {
        con.close();
        log.log(Logger.LoggerType.ERROR, "Database disconnected.", true);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean isConnected() {
    return (con != null);
  }
}
