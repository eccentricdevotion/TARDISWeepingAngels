/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.silent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityTeleportEvent;

/**
 *
 * @author eccentric_nz
 */
public class AntiTeleport implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onSilentTeleport(EntityTeleportEvent event) {
        final Entity ent = event.getEntity();
        if (ent.getType().equals(EntityType.ENDERMAN) && !ent.getPassengers().isEmpty() && ent.getPassengers().get(0) != null && ent.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSilentPickup(EntityChangeBlockEvent event) {
        final Entity ent = event.getEntity();
        if (ent.getType().equals(EntityType.ENDERMAN) && !ent.getPassengers().isEmpty() && ent.getPassengers().get(0) != null && ent.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
            event.setCancelled(true);
        }
    }
}
