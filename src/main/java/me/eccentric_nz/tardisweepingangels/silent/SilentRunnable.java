/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.silent;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.WorldGuardChecker;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author eccentric_nz
 */
public class SilentRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final MonsterEquipment equipper;

    public SilentRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
        equipper = new MonsterEquipment();
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("silent.worlds." + name) > 0) {
                // get the current silents
                List<Enderman> papal = new ArrayList<>();
                Collection<Enderman> mainframe = w.getEntitiesByClass(Enderman.class);
                mainframe.forEach((c) -> {
                    if (!c.getPassengers().isEmpty() && c.getPassengers().get(0) != null && c.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
                        papal.add(c);
                    }
                });
                // count the current cybermen
                if (papal.size() < plugin.getConfig().getInt("silent.worlds." + name)) {
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
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            if (!plugin.getNotOnWater().contains(l.getBlock().getBiome()) && WorldGuardChecker.canSpawn(l)) {
                LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.ENDERMAN);
                e.setSilent(true);
                e.setCanPickupItems(false);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    equipper.setSilentEquipment(e);
                    e.getPersistentDataContainer().set(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER, Monster.SILENT.getPersist());
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ENDERMAN, Monster.SILENT, l));
                }, 5L);
            }
        }
    }
}
