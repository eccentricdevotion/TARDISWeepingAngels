/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.death;

import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * @author eccentric_nz
 */
public class RainDamage implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onRainDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Enderman && !e.getPassengers().isEmpty() && e.getPassengers().get(0) != null && e.getPassengers().get(0).getType().equals(EntityType.GUARDIAN) && !event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
			event.setCancelled(true);
		}
	}
}
