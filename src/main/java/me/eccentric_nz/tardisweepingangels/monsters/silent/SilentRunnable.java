/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.silent;

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
import org.bukkit.entity.Skeleton;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;

/**
 * @author eccentric_nz
 */
public class SilentRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;

    public SilentRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = this.plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("silent.worlds." + name) > 0) {
                // get the current silents
                int papal = 0;
                Collection<Skeleton> mainframe = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton s : mainframe) {
                    PersistentDataContainer pdc = s.getPersistentDataContainer();
                    if (pdc.has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)) {
                        papal++;
                    }
                }
                if (papal < plugin.getConfig().getInt("silent.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnSilent(w);
                    }
                }
            }
        });
    }

    private void spawnSilent(World w) {
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
                LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.SKELETON);
                e.setSilent(true);
                e.setCanPickupItems(false);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    SilentEquipment.set(e, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.SKELETON, Monster.SILENT, l));
                }, 5L);
            }
        }
    }
}
