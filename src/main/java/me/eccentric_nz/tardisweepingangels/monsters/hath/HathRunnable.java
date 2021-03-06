/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.hath;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
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
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

/**
 * @author eccentric_nz
 */
public class HathRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;

    public HathRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("hath.worlds." + name) > 0) {
                // get the current warriors
                int hath = 0;
                Collection<Zombie> zombies = w.getEntitiesByClass(Zombie.class);
                for (Zombie c : zombies) {
                    PersistentDataContainer pdc = c.getPersistentDataContainer();
                    if (pdc.has(TARDISWeepingAngels.HATH, PersistentDataType.INTEGER)) {
                        hath++;
                    }
                }
                if (hath < plugin.getConfig().getInt("hath.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnHath(w);
                    }
                }
            }
        });
    }

    private void spawnHath(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[TARDISWeepingAngels.random.nextInt(chunks.length)];
            int x = c.getX() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int z = c.getZ() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            if (WaterChecker.isNotWater(l)) {
                if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null && !WorldGuardChecker.canSpawn(l)) {
                    return;
                }
                LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.ZOMBIFIED_PIGLIN);
                e.setSilent(true);
                Zombie cyber = (Zombie) e;
                cyber.setAdult();
                PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                cyber.addPotionEffect(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    HathEquipment.set(e, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIFIED_PIGLIN, Monster.HATH, l));
                }, 5L);
            }
        }
    }
}
