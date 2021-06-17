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
package me.eccentric_nz.tardisweepingangels.monsters.ood;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class VillagerSpawnListener implements Listener {

    private final TardisWeepingAngelsPlugin plugin;

    public VillagerSpawnListener(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVillagerSpawnEvent(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (!entity.getType().equals(EntityType.VILLAGER)) {
            return;
        }
        World world = entity.getWorld();
        if (!plugin.getConfig().getBoolean("ood.worlds." + world.getName())) {
            return;
        }
        if (TardisWeepingAngelsPlugin.random.nextInt(100) < plugin.getConfig().getInt("ood.spawn_from_villager")) {
            Entity ood = world.spawnEntity(entity.getLocation(), EntityType.ARMOR_STAND);
            OodEquipment.set(null, ood, false);
            plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(ood, EntityType.ARMOR_STAND, Monster.OOD, entity.getLocation()));
            entity.remove();
        }
    }
}
