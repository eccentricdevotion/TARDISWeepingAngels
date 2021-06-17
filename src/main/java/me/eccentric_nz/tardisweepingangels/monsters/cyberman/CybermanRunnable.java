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
package me.eccentric_nz.tardisweepingangels.monsters.cyberman;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

/**
 * @author eccentric_nz
 */
public class CybermanRunnable implements Runnable {

    private final TardisWeepingAngelsPlugin plugin;
    private final int spawnRate;

    public CybermanRunnable(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        spawnRate = plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((world) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(world.getName());
            if (plugin.getConfig().getInt("cybermen.worlds." + name) > 0) {
                // get the current warriors
                int cybermen = 0;
                Collection<Zombie> zombies = world.getEntitiesByClass(Zombie.class);
                for (Zombie cyberman : zombies) {
                    PersistentDataContainer persistentDataContainer = cyberman.getPersistentDataContainer();
                    if (persistentDataContainer.has(TardisWeepingAngelsPlugin.CYBERMAN, PersistentDataType.INTEGER)) {
                        cybermen++;
                    }
                }
                // count the current cybermen
                if (cybermen < plugin.getConfig().getInt("cybermen.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawnRate; i++) {
                        spawnCyberman(world);
                    }
                }
            }
        });
    }

    private void spawnCyberman(World world) {
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
                LivingEntity cyberman = (LivingEntity) world.spawnEntity(location, EntityType.ZOMBIE);
                cyberman.setSilent(true);
                Ageable ageable = (Ageable) cyberman;
                ageable.setAdult();
                PotionEffect potionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                ageable.addPotionEffect(potionEffect);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    CybermanEquipment.set(cyberman, false);
                    plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(cyberman, EntityType.ZOMBIE, Monster.CYBERMAN, location));
                }, 5L);
            }
        }
    }
}
