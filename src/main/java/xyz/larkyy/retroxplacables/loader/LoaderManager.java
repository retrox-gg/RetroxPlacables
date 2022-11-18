package xyz.larkyy.retroxplacables.loader;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import xyz.larkyy.retroxplacables.RetroxPlacables;
import xyz.larkyy.retroxplacables.loader.impl.ModelEngineLoader;

import java.util.ArrayList;
import java.util.List;

public class LoaderManager {

    private final List<Loader> loaders = new ArrayList<>();
    private final Runnable runnable;

    public LoaderManager(Runnable runnable) {
        this.runnable = runnable;
        if (getPlugin("ModelEngine") != null) {
            try {
                Class.forName("com.ticxo.modelengine.api.events.ModelRegistrationEvent");

                loaders.add(new ModelEngineLoader(
                        this::tryLoad
                ));
            } catch (ClassNotFoundException ignored) {
                Bukkit.getConsoleSender().sendMessage("[RetroxPlacables] Using an old version of ModelEngine! Use at least v3.0.0!");
            }
        }
    }

    public void tryLoad() {
        for (Loader loader : loaders) {
            if (!loader.isLoaded()) {
                return;
            }
        }
        load();
    }

    private void load() {
        runnable.run();
    }

    private Plugin getPlugin(String str) {
        return RetroxPlacables.getInstance().getServer().getPluginManager().getPlugin(str);
    }

}
