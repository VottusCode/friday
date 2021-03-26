package cz.maku.friday.command;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class FridayCommand implements CommandExecutor {

    @Getter
    private String commandName;

    public FridayCommand(String commandName) {
        this.commandName = commandName;
    }

    public abstract boolean onCmd(CommandSender sender, String cmd, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        onCmd(sender, commandLabel, args);
        return false;
    }
}