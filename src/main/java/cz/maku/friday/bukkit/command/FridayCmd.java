package cz.maku.friday.bukkit.command;

import cz.maku.friday.Friday;
import cz.maku.friday.player.FridayPlayer;
import cz.maku.friday.util.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FridayCmd extends cz.maku.friday.command.FridayCommand {

  public FridayCmd() {
    super("friday");
  }

  @Override
  public boolean onCmd(CommandSender sender, String cmd, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player) sender;
      FridayPlayer fp = FridayPlayer.get(p.getName());
      fp.sendSpace();
      fp.sendCenteredMessage(
        "§6§lF§e.§6§lR§e.§6§lI§e.§6§lD§e.§6§lA§e.§6§lY§e. §6§o(§e§o" +
        Friday.getPlugin().getDescription().getVersion() +
        "§6§o)"
      );
      fp.sendCenteredMessage("§7§oAPI for Core, managing the entire server...");
      fp.sendSpace();
      fp.sendMessage("   §e§o- by itIsMaku");
      fp.sendSpace();
    } else {
      sender.sendMessage(
        ChatColor.YELLOW +
        "FPs: " +
        Friday.getPlugin().getFridayPlayers().size()
      );
    }
    return false;
  }
}
