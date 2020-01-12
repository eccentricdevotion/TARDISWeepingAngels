/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.silurians;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.WorldGuardChecker;
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
import java.util.logging.Level;

/**
 * @author eccentric_nz
 */
public class SilurianRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;

    public SilurianRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("silurians.worlds." + name) > 0) {
                // check the world generator
                if (w.getGenerator() != null) {
                    plugin.getConfig().set("silurians.worlds." + name, 0);
                    plugin.saveConfig();
                    plugin.getServer().getLogger().log(Level.WARNING, "TARDISWeepingAngels cannot safely spawn Silurians in custom worlds!");
                } else {
                    // get the current silurian count
                    int silurians = 0;
                    Collection<Skeleton> skeletons = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton s : skeletons) {
                        PersistentDataContainer pdc = s.getPersistentDataContainer();
                        if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
                            silurians++;
                        }
                    }
                    if (silurians < plugin.getConfig().getInt("silurians.worlds." + name)) {
                        // if less than maximum, spawn some more
                        for (int i = 0; i < spawn_rate; i++) {
                            spawnSilurian(w);
                        }
                    }
                }
            }
        });
    }

    private void spawnSilurian(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[TARDISWeepingAngels.random.nextInt(chunks.length)];
            int x = c.getX() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int z = c.getZ() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            Location search = CaveFinder.searchCave(l);
            Location cave = ((search == null)) ? l : search;
            if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null && !WorldGuardChecker.canSpawn(cave)) {
                return;
            }
            LivingEntity e = (LivingEntity) w.spawnEntity(cave, EntityType.SKELETON);
            e.setSilent(true);
            PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
            e.addPotionEffect(p);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                SilurianEquipment.set(e, false);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.SKELETON, Monster.SILURIAN, cave));
            }, 5L);
        }
    }
}
