package cz.maku.friday.player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cz.maku.friday.Friday;
import cz.maku.friday.storage.sql.SQLValue;
import cz.maku.friday.util.CenteredMessage;
import cz.maku.friday.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class FridayPlayer {

  private final String playerName;

  public FridayPlayer(String playerName) {
    this.playerName = playerName;
  }

  public static FridayPlayer get(String nickname) {
    return Friday.getPlugin().getFridayPlayers().get(nickname);
  }

  public Player getPlayer() {
    return Bukkit.getPlayerExact(getName());
  }

  public String getName() {
    return this.playerName;
  }

  public boolean isOnline() {
    return Bukkit.getOfflinePlayer(getName()).isOnline();
  }

  public void sendMessage(String message) {
    getPlayer().sendMessage(message);
  }

  public void sendSpace() {
    sendMessage(" ");
  }

  public PlayerInventory getInventory() {
    return getPlayer().getInventory();
  }

  public void sendCenteredMessage(String message) {
    CenteredMessage.sendCenteredMessage(this, message);
  }

  public void sendNoPermission() {
    sendFormattedMessage(
      MessageFormat.ERROR,
      "Chyba, na toto nemáš dostatečná oprávnění!"
    );
  }

  public void executeCommand(String command, boolean console) {
    if (console) {
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    } else {
      getPlayer().performCommand(command);
    }
  }

  public void updatePermission(String permission, boolean set, boolean state) {
    if (set) {
      Bukkit.dispatchCommand(
        Bukkit.getConsoleSender(),
        "lp user " + playerName + " permission set " + permission + " " + state
      );
      Friday
        .getPlugin()
        .getLog()
        .log(
          Logger.LoggerType.SUCCESS,
          "Permission " +
          permission +
          " for player " +
          playerName +
          " was set to " +
          state +
          ".",
          false
        );
    } else {
      Bukkit.dispatchCommand(
        Bukkit.getConsoleSender(),
        "lp user " + playerName + " permission unset " + permission
      );
      Friday
        .getPlugin()
        .getLog()
        .log(
          Logger.LoggerType.SUCCESS,
          "Permission " +
          permission +
          " for player " +
          playerName +
          " was unset.",
          false
        );
    }
  }

  public void updateGroup(String group, String expire) {
    if (expire.equalsIgnoreCase("never")) {
      Bukkit.dispatchCommand(
        Bukkit.getConsoleSender(),
        "lp user " + playerName + " parent set " + group
      );
    } else {
      Bukkit.dispatchCommand(
        Bukkit.getConsoleSender(),
        "lp user " + playerName + " addtemp " + group + " " + expire
      );
    }
    Friday
      .getPlugin()
      .getLog()
      .log(
        Logger.LoggerType.SUCCESS,
        "Group " +
        group +
        " for player " +
        playerName +
        " was set to " +
        expire +
        ".",
        false
      );
  }

  public boolean isAdmin() {
    return Friday
      .getPlugin()
      .getAdminsConfig()
      .getStringList("admins")
      .contains(playerName);
  }

  public void open(Inventory inv) {
    getPlayer().openInventory(inv);
  }

  public void setOp(boolean state) {
    getPlayer().setOp(state);
    Friday
      .getPlugin()
      .getLog()
      .log(
        Logger.LoggerType.SUCCESS,
        "Op for player " + playerName + " was set to " + state + ".",
        false
      );
  }

  public void setFullPerms() {
    Bukkit.dispatchCommand(
      Bukkit.getConsoleSender(),
      "lp user " + playerName + " permission set * true"
    );
    Friday
      .getPlugin()
      .getLog()
      .log(
        Logger.LoggerType.SUCCESS,
        "Permission '*' for player " + playerName + " was set to true.",
        false
      );
  }

  public World getWorld() {
    return getPlayer().getWorld();
  }

  public void toWorld(World world) {
    Location loc = world.getSpawnLocation();
    getPlayer().teleport(loc);
    Friday
      .getPlugin()
      .getLog()
      .log(
        Logger.LoggerType.SUCCESS,
        "Player " +
        playerName +
        " was teleported to " +
        world +
        "'s spawnpoint.",
        false
      );
  }

  public void sendToServer(String server) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("Connect");
    out.writeUTF(server);
    getPlayer()
      .sendPluginMessage(Friday.getPlugin(), "BungeeCord", out.toByteArray());
    Friday
      .getPlugin()
      .getLog()
      .log(
        Logger.LoggerType.SUCCESS,
        "Player " + playerName + " was moved by bungee to " + server + ".",
        true
      );
  }

  public void kick(String reason) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("KickPlayer");
    out.writeUTF(playerName);
    out.writeUTF(reason);
    getPlayer()
      .sendPluginMessage(Friday.getPlugin(), "BungeeCord", out.toByteArray());
    Friday
      .getPlugin()
      .getLog()
      .log(
        Logger.LoggerType.SUCCESS,
        "Player " +
        playerName +
        " was kicked from bungee for reason " +
        reason +
        ".",
        true
      );
  }

  public void sendFormattedMessage(MessageFormat format, String message) {
    if (format.equals(MessageFormat.SUCCESS)) {
      sendMessage("§2■ §a" + message);
    } else if (format.equals(MessageFormat.ERROR)) {
      sendMessage("§4■ §c" + message);
    } else if (format.equals(MessageFormat.LOADING)) {
      sendMessage("§8■ §7" + message);
    } else if (format.equals(MessageFormat.WARNING)) {
      sendMessage("§6■ §e" + message);
    }
  }

  public boolean canBuy(String value, int key) {
    return loadSqlIntValue(value) >= key;
  }

  public void buy(String value, int key) {
    SQLValue sqlValue = new SQLValue(this, value);
    sqlValue.removeSqlValue(key);
  }

  public boolean hasPermission(String permission) {
    return getPlayer().hasPermission(permission);
  }

  public String loadSqlValue(String value) {
    return new SQLValue(this, value).loadSqlValue();
  }

  public int loadSqlIntValue(String value) {
    return new SQLValue(this, value).loadSqlIntValue();
  }

  public void updateSqlValue(String value, String key) {
    new SQLValue(this, value).updateSqlValue(key);
  }

  public void updateSqlValue(String value, int key) {
    new SQLValue(this, value).updateSqlValue(key);
  }
}
