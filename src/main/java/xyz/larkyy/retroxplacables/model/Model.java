package xyz.larkyy.retroxplacables.model;

import org.bukkit.Location;

public interface Model {

    void spawn(Location location);

    void despawn();

    void teleport(Location location);

    Location getLocation();

    String getId();
}
