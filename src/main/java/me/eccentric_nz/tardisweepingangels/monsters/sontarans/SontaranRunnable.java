/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.sontarans;

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
public class SontaranRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;

    public SontaranRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("sontarans.worlds." + name) > 0) {
                long time = w.getTime();
                // only spawn in day - times according to http://minecraft.gamepedia.com/Day-night_cycle
                if ((time > 0 && time < 13187) || time > 22812) {
                    // get the current warriors
                    int sontarans = 0;
                    Collection<Zombie> potatoes = w.getEntitiesByClass(Zombie.class);
                    for (Zombie pz : potatoes) {
                        PersistentDataContainer pdc = pz.getPersistentDataContainer();
                        if (pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                            sontarans++;
                        }
                    }
                    // count the current sontarans
                    if (sontarans < plugin.getConfig().getInt("sontarans.worlds." + name)) {
                        // if less than maximum, spawn some more
                        for (int i = 0; i < spawn_rate; i++) {
                            spawnSontaran(w);
                        }
                    }
                }
            }
        });
    }

    private void spawnSontaran(World w) {
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
                Zombie sontaran = (Zombie) e;
                sontaran.setBaby(false);
                PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                sontaran.addPotionEffect(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    SontaranEquipment.set(e, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIE, Monster.SONTARAN, l));
                }, 5L);
            }
        }
    }
}
