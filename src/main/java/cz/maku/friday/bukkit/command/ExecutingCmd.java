package cz.maku.friday.bukkit.command;

import cz.maku.friday.Friday;
import cz.maku.friday.command.FridayCommand;
import cz.maku.friday.player.FridayPlayer;
import cz.maku.friday.player.MessageFormat;
import cz.maku.friday.storage.config.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExecutingCmd extends FridayCommand {

  public ExecutingCmd() {
    super("executing");
  }

  @Override
  public boolean onCmd(CommandSender sender, String cmd, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player) sender;
      FridayPlayer fp = FridayPlayer.get(p.getName());
      if (fp.isAdmin()) {
        if (args.length < 1) {
          fp.sendFormattedMessage(
            MessageFormat.ERROR,
            "Musíš zadat hráče, s kterým chceš manipulovat."
          );
        } else {
          FridayPlayer t = FridayPlayer.get(args[0]);
          fp.sendFormattedMessage(
            MessageFormat.SUCCESS,
            "Hráč " + t.getName() + " byl zvolen pro manipulaci."
          );
          if (args.length < 2) {
            fp.sendFormattedMessage(
              MessageFormat.ERROR,
              "Musíš zadat číslo manipulace."
            );
          } else {
            String manipulation = args[1];
            if (manipulation.equalsIgnoreCase("1")) {
              if (args.length < 3) {
                fp.sendFormattedMessage(
                  MessageFormat.ERROR,
                  "Musíš zadat state."
                );
              } else {
                if (
                  args[2].equalsIgnoreCase("true") ||
                  args[2].equalsIgnoreCase("false")
                ) {
                  boolean state = Boolean.parseBoolean(args[2]);
                  t.setOp(state);
                  fp.sendFormattedMessage(
                    MessageFormat.SUCCESS,
                    "Hráči " +
                    t.getName() +
                    " byla nastavena operátorská práva na " +
                    state +
                    "."
                  );
                } else {
                  fp.sendFormattedMessage(
                    MessageFormat.ERROR,
                    "Musíš zadat state."
                  );
                }
              }
            } else if (manipulation.equalsIgnoreCase("2")) {
              if (args.length < 3) {
                fp.sendFormattedMessage(
                  MessageFormat.ERROR,
                  "Musíš zadat state."
                );
              } else {
                Config admins = Friday.getPlugin().getAdminsConfig();
                if (args[2].equalsIgnoreCase("true")) {
                  if (!admins.getStringList("admins").contains(t.getName())) {
                    admins.addToStringList("admins", t.getName());
                    fp.sendFormattedMessage(
                      MessageFormat.SUCCESS,
                      "Hráči " +
                      t.getName() +
                      " byl nastaven status administrátor F.R.I.D.A.Y. na " +
                      args[2] +
                      "."
                    );
                  } else {
                    fp.sendFormattedMessage(
                      MessageFormat.ERROR,
                      "Hráč " +
                      t.getName() +
                      " již je F.R.I.D.A.Y. administrátor."
                    );
                  }
                } else if (args[2].equalsIgnoreCase("false")) {
                  if (admins.getStringList("admins").contains(t.getName())) {
                    admins.removeFromStringList("admins", t.getName());
                    fp.sendFormattedMessage(
                      MessageFormat.SUCCESS,
                      "Hráči " +
                      t.getName() +
                      " byl nastaven status administrátor F.R.I.D.A.Y. na " +
                      args[2] +
                      "."
                    );
                  } else {
                    fp.sendFormattedMessage(
                      MessageFormat.ERROR,
                      "Hráč " +
                      t.getName() +
                      " není F.R.I.D.A.Y. administrátor."
                    );
                  }
                } else {
                  fp.sendFormattedMessage(
                    MessageFormat.ERROR,
                    "Musíš zadat state."
                  );
                }
              }
            }
          }
        }
      } else {
        fp.sendNoPermission();
      }
    }
    return false;
  }
}
