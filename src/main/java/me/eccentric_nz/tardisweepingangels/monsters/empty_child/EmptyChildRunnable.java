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
package me.eccentric_nz.tardisweepingangels.monsters.empty_child;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.WaterChecker;
import me.eccentric_nz.tardisweepingangels.utils.WorldGuardChecker;
import me.eccentric_nz.tardisweepingangels.utils.WorldProcessor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;

/**
 * @author eccentric_nz
 */
public class EmptyChildRunnable implements Runnable {

    private final TardisWeepingAngelsPlugin plugin;
    private final int spawnRate;

    public EmptyChildRunnable(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        spawnRate = plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((world) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(world.getName());
            if (plugin.getConfig().getInt("empty_child.worlds." + name) > 0) {
                // get the current empty children
                int emptyChildren = 0;
                Collection<Zombie> zombies = world.getEntitiesByClass(Zombie.class);
                for (Zombie emptyChild : zombies) {
                    PersistentDataContainer persistentDataContainer = emptyChild.getPersistentDataContainer();
                    if (persistentDataContainer.has(TardisWeepingAngelsPlugin.EMPTY, PersistentDataType.INTEGER)) {
                        emptyChildren++;
                    }
                }
                // count the current empty children
                if (emptyChildren < plugin.getConfig().getInt("empty_child.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawnRate; i++) {
                        spawnEmptyChild(world);
                    }
                }
            }
        });
    }

    private void spawnEmptyChild(World world) {
        Chunk[] chunks = world.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk chunk = chunks[TardisWeepingAngelsPlugin.random.nextInt(chunks.length)];
            int x = chunk.getX() * 16 + TardisWeepingAngelsPlugin.random.nextInt(16);
            int z = chunk.getZ() * 16 + TardisWeepingAngelsPlugin.random.nextInt(16);
            int y = world.getHighestBlockYAt(x, z);
            Location location = new Location(world, x, y + 1, z);
            if (WaterChecker.isNotWater(location)) {
                if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null && !WorldGuardChecker.canSpawn(location)) {
                    return;
                }
                LivingEntity emptyChild = (LivingEntity) world.spawnEntity(location, EntityType.ZOMBIE);
                emptyChild.setSilent(true);
                Ageable ageable = (Ageable) emptyChild;
                ageable.setBaby();
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    EmptyChildEquipment.set(emptyChild, false);
                    plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(emptyChild, EntityType.ZOMBIE, Monster.EMPTY_CHILD, location));
                }, 5L);
            }
        }
    }
}
