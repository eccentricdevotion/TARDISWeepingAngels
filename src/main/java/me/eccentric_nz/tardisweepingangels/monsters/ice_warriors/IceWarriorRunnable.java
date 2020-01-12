/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.ice_warriors;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
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

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final List<Biome> biomes = new ArrayList<>();

    public IceWarriorRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
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
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("ice_warriors.worlds." + name) > 0) {
                long time = w.getTime();
                // only spawn in day - times according to http://minecraft.gamepedia.com/Day-night_cycle
                if ((time > 0 && time < 13187) || time > 22812) {
                    // get the current warriors
                    int warriors = 0;
                    Collection<PigZombie> piggies = w.getEntitiesByClass(PigZombie.class);
                    for (PigZombie pz : piggies) {
                        PersistentDataContainer pdc = pz.getPersistentDataContainer();
                        if (pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)) {
                            warriors++;
                        }
                    }
                    // count the current warriors
                    if (warriors < plugin.getConfig().getInt("ice_warriors.worlds." + name)) {
                        // if less than maximum, spawn some more
                        for (int i = 0; i < spawn_rate; i++) {
                            spawnIceWarrior(w);
                        }
                    }
                }
            }
        });
    }

    private void spawnIceWarrior(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[TARDISWeepingAngels.random.nextInt(chunks.length)];
            int x = c.getX() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int z = c.getZ() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            if (biomes.contains(l.getBlock().getBiome())) {
                if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null && !WorldGuardChecker.canSpawn(l)) {
                    return;
                }
                LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.PIG_ZOMBIE);
                e.setSilent(true);
                PigZombie warrior = (PigZombie) e;
                warrior.setAngry(true);
                warrior.setAnger(Integer.MAX_VALUE);
                PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                warrior.addPotionEffect(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    IceWarriorEquipment.set(e, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.PIG_ZOMBIE, Monster.ICE_WARRIOR, l));
                }, 5L);
            }
        }
    }
}
