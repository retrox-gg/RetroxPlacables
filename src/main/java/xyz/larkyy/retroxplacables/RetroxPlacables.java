package xyz.larkyy.retroxplacables;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.larkyy.aquaticeventlistener.AquaticEventListener;
import xyz.larkyy.retroxplacables.commands.Commands;
import xyz.larkyy.retroxplacables.loader.LoaderManager;
import xyz.larkyy.retroxplacables.model.Models;

public final class RetroxPlacables extends JavaPlugin {

    private static RetroxPlacables instance;
    private Models models;
    private EditingPlayers editingPlayers;
    @Override
    public void onEnable() {
        instance = this;
        AquaticEventListener.init(this);
        models = new Models();
        editingPlayers = new EditingPlayers();
        getCommand("retroxplacables").setExecutor(new Commands());

        new LoaderManager(
                () -> new BukkitRunnable() {
                    @Override
                    public void run() {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                models.load();
                            }
                        }.runTaskLater(getInstance(),1);
                    }
                }.runTask(this)
        );
    }

    @Override
    public void onDisable() {
        models.despawn();
        Bukkit.getWorlds().forEach(world -> {
            world.getEntities().forEach(entity -> {
                if ("RetroxPlacables".equals(entity.getCustomName())) {
                    entity.setPersistent(false);
                    entity.remove();
                }
            });
        });
    }

    public static RetroxPlacables getInstance() {
        return instance;
    }

    public Models getModels() {
        return models;
    }

    public EditingPlayers getEditingPlayers() {
        return editingPlayers;
    }
}
