package xyz.larkyy.retroxplacables.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.larkyy.retroxplacables.RetroxPlacables;
import xyz.larkyy.retroxplacables.commands.ICommand;

public class RemoveCommand implements ICommand {
    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            return;
        }

        if (!sender.hasPermission("retroxplacables.use")) {
            p.sendMessage("You do not have permissions to do that!");
            return;
        }

        if (args.length < 2) {
            p.sendMessage("You must specify the model identifier!");
            return;
        }

        var model = RetroxPlacables.getInstance().getModels().getModel(args[1]);
        if (model == null) {
            p.sendMessage("Unknown model...");
            return;
        }
        RetroxPlacables.getInstance().getModels().removeModel(args[1]);
        p.sendMessage("Model has been removed!");
    }
}
