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
package me.eccentric_nz.tardisweepingangels.monsters.toclafane;

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
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Collection;

public class ToclafaneRunnable implements Runnable {

    private final TardisWeepingAngelsPlugin plugin;
    private final int spawnRate;

    public ToclafaneRunnable(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        spawnRate = this.plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((world) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(world.getName());
            if (plugin.getConfig().getInt("toclafane.worlds." + name) > 0) {
                // get the current toclafane
                int toclafanes = 0;
                Collection<Bee> bees = world.getEntitiesByClass(Bee.class);
                for (Bee toclafane : bees) {
                    if (toclafane.getPassengers().size() > 0 && toclafane.getPassengers().get(0) instanceof ArmorStand) {
                        toclafanes++;
                    }
                }
                if (toclafanes < plugin.getConfig().getInt("toclafane.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawnRate; i++) {
                        spawnToclafane(world);
                    }
                }
            }
        });
    }

    private void spawnToclafane(World world) {
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
                Entity toclafane = world.spawnEntity(location, EntityType.ARMOR_STAND);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    ToclafaneEquipment.set(toclafane, false);
                    plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(toclafane, EntityType.ARMOR_STAND, Monster.TOCLAFANE, location));
                }, 5L);
            }
        }
    }
}
