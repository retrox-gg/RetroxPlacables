package xyz.larkyy.retroxplacables.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.larkyy.retroxplacables.Config;
import xyz.larkyy.retroxplacables.RetroxPlacables;
import xyz.larkyy.retroxplacables.model.factory.ASModelFactory;
import xyz.larkyy.retroxplacables.model.factory.MEModelFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Models {

    private final Map<String,ModelFactory> factories;
    private final Map<String,Model> models;
    private final Config config = new Config(RetroxPlacables.getInstance(),"data.yml");

    public Models() {
        factories = new HashMap<>();
        models = new HashMap<>();
        loadDefaultFactories();
    }

    private void loadDefaultFactories() {
        factories.put("armorstand",new ASModelFactory());
        factories.put("modelengine",new MEModelFactory());
    }

    public void load() {
        config.load();

        FileConfiguration cfg = config.getConfiguration();
        if (!cfg.contains("models")) {
            return;
        }
        for (String str : cfg.getConfigurationSection("models").getKeys(false)) {
            var model = loadModel(cfg, "models." + str);
            if (model == null) {
                continue;
            }
            String[] locationStr = cfg.getString("models."+str+".location").split(":");
            World w = Bukkit.getWorld(locationStr[0]);
            if (w == null) {
                continue;
            }
            double x = Double.parseDouble(locationStr[1]);
            double y = Double.parseDouble(locationStr[2]);
            double z = Double.parseDouble(locationStr[3]);
            float yaw = Float.parseFloat(locationStr[4]);
            Location location = new Location(w,x,y,z,yaw,0);

            models.put(model.getId(),model);
            model.spawn(location);
        }
    }

    private Model loadModel(FileConfiguration cfg, String path) {
        String factory = cfg.getString(path+".factory");
        List<String> arguments = new ArrayList<>(List.of(cfg.getString(path+".args").split(":")));

        return create(factory,arguments);
    }

    public void addFactory(String identifier, ModelFactory factory) {
        factories.put(identifier.toLowerCase(),factory);
    }

    public void addModel(Location location, Model model) {
        models.put(model.getId(),model);
        model.spawn(location);
    }

    public void saveModel(Location location, String factory, Model model, List<String> args) {
        FileConfiguration cfg = config.getConfiguration();

        String path = "models."+model.getId()+".";
        cfg.set(path+"factory",factory);
        String locationStr = location.getWorld().getName()+":"+location.getX()+":"+location.getY()+":"+location.getZ()+":"+location.getYaw();
        cfg.set(path+"location",locationStr);

        StringBuilder builder = new StringBuilder();
        args.forEach(str-> {
            builder.append(str).append(":");
        });
        cfg.set(path+"args",builder.toString());

        config.save();
    }

    public void removeModel(String identifier) {
        var model = models.remove(identifier);
        if (model != null) {
            model.despawn();
            config.getConfiguration().set("models."+identifier,null);
            config.save();
        }
    }

    public Model getModel(String identifier) {
        return models.get(identifier);
    }

    public Model create(String factory, List<String> arguments) {
        if (factory == null) {
            return null;
        }
        ModelFactory mf = factories.get(factory.toLowerCase());
        if (mf == null) {
            return null;
        }
        return mf.create(arguments);
    }

    public void despawn() {
        models.values().forEach(Model::despawn);
        models.clear();
    }

    public Config getConfig() {
        return config;
    }
}
