package xyz.larkyy.retroxplacables.model.factory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.larkyy.retroxplacables.model.Model;
import xyz.larkyy.retroxplacables.model.ModelFactory;
import xyz.larkyy.retroxplacables.model.impl.ASModel;

import java.util.List;

public class ASModelFactory implements ModelFactory {
    @Override
    public Model create(List<String> args) {
        if (args.size() < 3) {
            return null;
        }
        String id = args.get(0);

        Material m = Material.valueOf(args.get(1).toUpperCase());
        ItemStack is = new ItemStack(m);
        ItemMeta im = is.getItemMeta();
        im.setCustomModelData(Integer.parseInt(args.get(2)));
        is.setItemMeta(im);

        return new ASModel(id,is);
    }
}
