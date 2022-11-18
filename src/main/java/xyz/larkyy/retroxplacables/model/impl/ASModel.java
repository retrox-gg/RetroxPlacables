package xyz.larkyy.retroxplacables.model.impl;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import xyz.larkyy.retroxplacables.model.Model;

public class ASModel implements Model {

    private final ItemStack is;
    private final String id;

    private ArmorStand armorStand;

    public ASModel(String id, ItemStack is) {
        this.id = id;
        this.is = is;
    }

    @Override
    public void spawn(Location location) {
        if (armorStand != null) {
            despawn();
        }
        spawnAs(location);
    }

    private void spawnAs(Location location) {
        Location loc = location.clone();

        armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND, CreatureSpawnEvent.SpawnReason.CUSTOM, e -> {
            ArmorStand as = (ArmorStand) e;
            as.setCustomName("RetroxPlacables");
            as.setInvisible(true);
            as.getEquipment().setHelmet(is);
            as.setCanTick(false);
            as.setCanMove(false);
            as.setMarker(true);
            as.setSilent(true);
        });
    }

    @Override
    public void despawn() {
        if (armorStand != null) {
            armorStand.remove();
        }
    }

    @Override
    public void teleport(Location location) {
        armorStand.teleport(location);
    }

    @Override
    public Location getLocation() {
        if (armorStand == null) {
            return null;
        }
        return armorStand.getLocation();
    }

    @Override
    public String getId() {
        return id;
    }
}
