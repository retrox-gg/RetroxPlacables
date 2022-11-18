package xyz.larkyy.retroxplacables.loader.impl;

import com.ticxo.modelengine.api.events.ModelRegistrationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.larkyy.retroxplacables.RetroxPlacables;
import xyz.larkyy.retroxplacables.loader.Loader;

public class ModelEngineLoader extends Loader implements Listener {


    public ModelEngineLoader(Runnable runnable) {
        super(runnable);
        RetroxPlacables.getInstance().getServer().getPluginManager().registerEvents(this,RetroxPlacables.getInstance());
    }

    @EventHandler
    public void onModelsLoad(ModelRegistrationEvent e) {
        if (e.getPhase().equals(ModelRegistrationEvent.Phase.FINAL)) {
            setLoaded(true);
            getRunnable().run();
        }
    }
}
