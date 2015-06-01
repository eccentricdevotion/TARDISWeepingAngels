/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.silent;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;

/**
 *
 * @author eccentric_nz
 */
public class AntiTeleport implements Listener {

    private final TARDISWeepingAngels plugin;

    public AntiTeleport(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEndermanTeleport(EntityTeleportEvent event) {
        final Entity ent = event.getEntity();
        if (ent.getType().equals(EntityType.ENDERMAN) && ent.getPassenger() != null && ent.getPassenger().getType().equals(EntityType.GUARDIAN)) {
            event.setCancelled(true);
        }
    }
}
