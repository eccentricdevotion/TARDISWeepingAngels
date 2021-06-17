/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
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
        Entity entity = event.getEntity();
        if (entity instanceof Enderman && !entity.getPassengers().isEmpty() && entity.getPassengers().get(0) != null && entity.getPassengers().get(0).getType().equals(EntityType.GUARDIAN) && !event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
            event.setCancelled(true);
        }
    }
}
