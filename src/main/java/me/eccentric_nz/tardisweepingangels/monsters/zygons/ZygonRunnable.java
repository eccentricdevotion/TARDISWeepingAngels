/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.zygons;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
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
public class ZygonRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;

    public ZygonRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("zygons.worlds." + name) > 0) {
                // get the current warriors
                int zygons = 0;
                Collection<Zombie> zombies = w.getEntitiesByClass(Zombie.class);
                for (Zombie z : zombies) {
                    PersistentDataContainer pdc = z.getPersistentDataContainer();
                    if (pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                        zygons++;
                    }
                }
                if (zygons < plugin.getConfig().getInt("zygons.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnZygon(w);
                    }
                }
            }
        });
    }

    private void spawnZygon(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[TARDISWeepingAngels.random.nextInt(chunks.length)];
            int x = c.getX() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int z = c.getZ() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            if (!plugin.getNotOnWater().contains(l.getBlock().getBiome())) {
                if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null && !WorldGuardChecker.canSpawn(l)) {
                    return;
                }
                LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.ZOMBIE);
                e.setSilent(true);
                Zombie zygon = (Zombie) e;
                zygon.setBaby(false);
                PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                zygon.addPotionEffect(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    ZygonEquipment.set(e, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIE, Monster.ZYGON, l));
                }, 5L);
            }
        }
    }
}
