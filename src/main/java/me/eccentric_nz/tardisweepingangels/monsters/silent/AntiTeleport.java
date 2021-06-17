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
package me.eccentric_nz.tardisweepingangels.monsters.silent;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
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

    private final TardisWeepingAngelsPlugin plugin;

    public AntiTeleport(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onSilentTeleport(EntityTeleportEvent event) {
        Entity eventEntity = event.getEntity();
        if (eventEntity.getType().equals(EntityType.ENDERMAN) && !eventEntity.getPassengers().isEmpty() && eventEntity.getPassengers().get(0) != null && eventEntity.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
            event.setCancelled(true);
        }
        if (eventEntity instanceof Wolf) {
            long delay = 50L;
            // schedule delayed task
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                for (Entity entity : eventEntity.getNearbyEntities(16, 16, 16)) {
                    if (entity instanceof Player player) {
                        player.playSound(eventEntity.getLocation(), "k9", 1.0f, 1.0f);
                    }
                }
            }, delay);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSilentPickup(EntityChangeBlockEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType().equals(EntityType.ENDERMAN) && !entity.getPassengers().isEmpty() && entity.getPassengers().get(0) != null && entity.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
            event.setCancelled(true);
        }
    }
}
