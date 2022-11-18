package xyz.larkyy.retroxplacables.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.larkyy.retroxplacables.commands.impl.EditCommand;
import xyz.larkyy.retroxplacables.commands.impl.RemoveCommand;
import xyz.larkyy.retroxplacables.commands.impl.SpawnCommand;

import java.util.HashMap;
import java.util.Map;

public class Commands implements CommandExecutor {

    private final Map<String, ICommand> availableCommands = new HashMap<>(){
        {
            put("edit",new EditCommand());
            put("spawn",new SpawnCommand());
            put("remove",new RemoveCommand());
        }
    };

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 1) {
            return false;
        }

        ICommand cmd = availableCommands.get(args[0]);
        if (cmd == null) {
            return false;
        }
        cmd.run(sender,args);
        return true;
    }
}
