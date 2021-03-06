/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.silent;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityTeleportEvent;

/**
 * @author eccentric_nz
 */
public class AntiTeleport implements Listener {

    private final TARDISWeepingAngels plugin;

    public AntiTeleport(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onSilentTeleport(EntityTeleportEvent event) {
        Entity ent = event.getEntity();
        if (ent.getType().equals(EntityType.ENDERMAN) && !ent.getPassengers().isEmpty() && ent.getPassengers().get(0) != null && ent.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
            event.setCancelled(true);
        }
        if (ent instanceof Wolf) {
            long delay = 50L;
            // schedule delayed task
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                for (Entity le : ent.getNearbyEntities(16, 16, 16)) {
                    if (le instanceof Player player) {
                        player.playSound(ent.getLocation(), "k9", 1.0f, 1.0f);
                    }
                }
            }, delay);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSilentPickup(EntityChangeBlockEvent event) {
        Entity ent = event.getEntity();
        if (ent.getType().equals(EntityType.ENDERMAN) && !ent.getPassengers().isEmpty() && ent.getPassengers().get(0) != null && ent.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
            event.setCancelled(true);
        }
    }
}
