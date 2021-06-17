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
package me.eccentric_nz.tardisweepingangels.monsters.ice_warrior;

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
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author eccentric_nz
 */
public class IceWarriorRunnable implements Runnable {

    private final TardisWeepingAngelsPlugin plugin;
    private final int spawnRate;
    private final List<Biome> biomes = new ArrayList<>();

    public IceWarriorRunnable(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        spawnRate = plugin.getConfig().getInt("spawn_rate.how_many");
        biomes.add(Biome.DEEP_FROZEN_OCEAN);
        biomes.add(Biome.FROZEN_OCEAN);
        biomes.add(Biome.FROZEN_RIVER);
        biomes.add(Biome.ICE_SPIKES);
        biomes.add(Biome.SNOWY_BEACH);
        biomes.add(Biome.SNOWY_MOUNTAINS);
        biomes.add(Biome.SNOWY_TAIGA);
        biomes.add(Biome.SNOWY_TAIGA_HILLS);
        biomes.add(Biome.SNOWY_TAIGA_MOUNTAINS);
        biomes.add(Biome.SNOWY_TUNDRA);
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((world) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(world.getName());
            if (plugin.getConfig().getInt("ice_warriors.worlds." + name) > 0) {
                long time = world.getTime();
                // only spawn in day - times according to http://minecraft.gamepedia.com/Day-night_cycle
                if ((time > 0 && time < 13187) || time > 22812) {
                    // get the current ice warriors
                    int iceWarriors = 0;
                    Collection<PigZombie> pigZombies = world.getEntitiesByClass(PigZombie.class);
                    for (PigZombie iceWarrior : pigZombies) {
                        PersistentDataContainer persistentDataContainer = iceWarrior.getPersistentDataContainer();
                        if (persistentDataContainer.has(TardisWeepingAngelsPlugin.ICE_WARRIOR, PersistentDataType.INTEGER)) {
                            iceWarriors++;
                        }
                    }
                    // count the current warriors
                    if (iceWarriors < plugin.getConfig().getInt("ice_warriors.worlds." + name)) {
                        // if less than maximum, spawn some more
                        for (int i = 0; i < spawnRate; i++) {
                            spawnIceWarrior(world);
                        }
                    }
                }
            }
        });
    }

    private void spawnIceWarrior(World world) {
        Chunk[] chunks = world.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk chunk = chunks[TardisWeepingAngelsPlugin.random.nextInt(chunks.length)];
            int x = chunk.getX() * 16 + TardisWeepingAngelsPlugin.random.nextInt(16);
            int z = chunk.getZ() * 16 + TardisWeepingAngelsPlugin.random.nextInt(16);
            int y = world.getHighestBlockYAt(x, z);
            Location location = new Location(world, x, y + 1, z);
            if (biomes.contains(location.getBlock().getBiome()) && WaterChecker.isNotWater(location)) {
                if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null && !WorldGuardChecker.canSpawn(location)) {
                    return;
                }
                LivingEntity iceWarrior = (LivingEntity) world.spawnEntity(location, EntityType.ZOMBIFIED_PIGLIN);
                iceWarrior.setSilent(true);
                PigZombie pigZombie = (PigZombie) iceWarrior;
                pigZombie.setAngry(true);
                pigZombie.setAnger(Integer.MAX_VALUE);
                PotionEffect potionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                pigZombie.addPotionEffect(potionEffect);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    IceWarriorEquipment.set(iceWarrior, false);
                    plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(iceWarrior, EntityType.ZOMBIFIED_PIGLIN, Monster.ICE_WARRIOR, location));
                }, 5L);
            }
        }
    }
}
