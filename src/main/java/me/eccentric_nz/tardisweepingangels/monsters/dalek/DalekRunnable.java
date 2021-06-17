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
package me.eccentric_nz.tardisweepingangels.monsters.dalek;

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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

/**
 * @author eccentric_nz
 */
public class DalekRunnable implements Runnable {

    private final TardisWeepingAngelsPlugin plugin;
    private final int spawnRate;

    public DalekRunnable(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        spawnRate = plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((world) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(world.getName());
            if (plugin.getConfig().getInt("daleks.worlds." + name) > 0) {
                // get the current daleks
                int daleks = 0;
                Collection<Skeleton> skeletons = world.getEntitiesByClass(Skeleton.class);
                for (Skeleton dalek : skeletons) {
                    PersistentDataContainer persistentDataContainer = dalek.getPersistentDataContainer();
                    if (persistentDataContainer.has(TardisWeepingAngelsPlugin.DALEK, PersistentDataType.INTEGER)) {
                        daleks++;
                    }
                }
                // count the current daleks
                if (daleks < plugin.getConfig().getInt("daleks.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawnRate; i++) {
                        spawnDalek(world);
                    }
                }
            }
        });
    }

    private void spawnDalek(World world) {
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
                LivingEntity dalek = (LivingEntity) world.spawnEntity(location, EntityType.SKELETON);
                dalek.setSilent(true);
                PotionEffect potionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                dalek.addPotionEffect(potionEffect);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    DalekEquipment.set(dalek, false);
                    plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(dalek, EntityType.SKELETON, Monster.DALEK, location));
                }, 5L);
            }
        }
    }
}
