/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.cybermen;

import java.util.Collection;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.Equipper;
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

/**
 * @author eccentric_nz
 */
public class CybermanRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;

    public CybermanRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("cybermen.worlds." + name) > 0) {
                // get the current warriors
                int cyberarmy = 0;
                Collection<Zombie> zombies = w.getEntitiesByClass(Zombie.class);
                for (Zombie c : zombies) {
                    PersistentDataContainer pdc = c.getPersistentDataContainer();
                    if (pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)) {
                        cyberarmy++;
                    }
                }
                if (cyberarmy < plugin.getConfig().getInt("cybermen.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnCyberman(w);
                    }
                }
            }
        });
    }

    private void spawnCyberman(World world) {
        Chunk[] chunks = world.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk chunk = chunks[TARDISWeepingAngels.random.nextInt(chunks.length)];
            int x = chunk.getX() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int z = chunk.getZ() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int y = world.getHighestBlockYAt(x, z);
            Location l = new Location(world, x, y + 1, z);
            if (WaterChecker.isNotWater(l)) {
                if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null && !WorldGuardChecker.canSpawn(l)) {
                    return;
                }
                LivingEntity c = (LivingEntity) world.spawnEntity(l, EntityType.ZOMBIE);
                c.setSilent(true);
                Ageable cyber = (Ageable) c;
                cyber.setAdult();
                PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                cyber.addPotionEffect(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    new Equipper(Monster.CYBERMAN, c, false, false).setHelmetAndInvisibilty();
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(c, EntityType.ZOMBIE, Monster.CYBERMAN, l));
                }, 5L);
            }
        }
    }
}
