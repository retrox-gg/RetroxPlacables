package xyz.larkyy.retroxplacables.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.larkyy.retroxplacables.RetroxPlacables;
import xyz.larkyy.retroxplacables.commands.ICommand;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand implements ICommand {
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
        if (args.length < 3) {
            p.sendMessage("You must specify the model factory identifier!");
            return;
        }
        List<String> arguments = new ArrayList<>(List.of(args));
        arguments.remove(0);
        arguments.remove(1);

        var model = RetroxPlacables.getInstance().getModels().create(args[2],arguments);
        if (model == null) {
            p.sendMessage("The model is unknown!");
            return;
        }
        Location loc = p.getLocation();

        loc.setYaw(Math.round(loc.getYaw()/90)*90);
        loc.setPitch(0);
        RetroxPlacables.getInstance().getModels().addModel(loc,model);
        RetroxPlacables.getInstance().getModels().saveModel(loc,args[2],model,arguments);
        p.sendMessage("Model has been added!");
    }
}
