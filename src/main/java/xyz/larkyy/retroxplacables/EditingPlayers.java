package xyz.larkyy.retroxplacables;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.larkyy.aquaticeventlistener.EventRegistry;
import xyz.larkyy.retroxplacables.model.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditingPlayers {

    private final Map<UUID, Model> players;

    public EditingPlayers() {
        players = new HashMap<>();
        registerEvent();
    }

    private void registerEvent() {
        EventRegistry.get().register(PlayerItemHeldEvent.class, e-> {
            if (!players.containsKey(e.getPlayer().getUniqueId())) {
                return;
            }
            Player p = e.getPlayer();
            String face;
            if (p.isSneaking()) {
                face = "ROTATE";
            }
            else if (p.getLocation().getPitch() >= 70 || p.getLocation().getPitch() <= -70) {
                face = "Y";
            } else {
                switch (p.getFacing()) {
                    case EAST, WEST -> {
                        face = "X";
                    }
                    case SOUTH, NORTH -> {
                        face = "Z";
                    }
                    default -> {
                        face = "Y";
                    }
                }
            }
            boolean lower = e.getPreviousSlot() == 0 && e.getNewSlot() == 8 ||
                    (e.getPreviousSlot() > e.getNewSlot() && !(e.getPreviousSlot() == 8 && e.getNewSlot() == 0));

            Model model = players.get(p.getUniqueId());
            var loc = model.getLocation();
            if (loc == null) {
                return;
            }

            switch (face) {
                case "Y" -> {
                    loc.add(0,!lower ? -0.1 : 0.1,0);
                }
                case "X" -> {
                    loc.add(!lower ? -0.1 : 0.1,0,0);
                }
                case "Z" -> {
                    loc.add(0,0,!lower ? -0.1 : 0.1);
                }
                case "ROTATE" -> {
                    loc.setYaw(loc.getYaw()+15);
                }
            }
            model.teleport(loc);

        });
        EventRegistry.get().register(AsyncPlayerChatEvent.class, e-> {
            if (!players.containsKey(e.getPlayer().getUniqueId())) {
                return;
            }
            Player p = e.getPlayer();
            if (e.getMessage().equalsIgnoreCase("done")) {
                e.setCancelled(true);
                ejectPlayer(p);
                p.sendMessage("Editing Finished!");
            }
        });
        EventRegistry.get().register(PlayerQuitEvent.class, e-> {
            if (!players.containsKey(e.getPlayer().getUniqueId())) {
                return;
            }
            Player p = e.getPlayer();
            ejectPlayer(p);
        });
    }

    public void injectPlayer(Player player, Model model) {
        players.put(player.getUniqueId(),model);
    }

    public void ejectPlayer(Player player) {
        players.remove(player.getUniqueId());
    }

}
