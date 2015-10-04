/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.silent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

/**
 *
 * @author eccentric_nz
 */
public class SilentRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final MonsterEquipment equipper;

    public SilentRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
        this.equipper = new MonsterEquipment();
    }

    @Override
    public void run() {
        for (World w : plugin.getServer().getWorlds()) {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("silent.worlds." + name) > 0) {
                // get the current warriors
                List<Enderman> papal = new ArrayList<Enderman>();
                Collection<Enderman> mainframe = w.getEntitiesByClass(Enderman.class);
                for (Enderman c : mainframe) {
                    if (c.getPassenger() != null && c.getPassenger().getType().equals(EntityType.GUARDIAN)) {
                        papal.add(c);
                    }
                }
                // count the current cybermen
                if (papal.size() < plugin.getConfig().getInt("silent.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnSilent(w);
                    }
                }
            }
        }
    }

    private void spawnSilent(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            if (!plugin.getNotOnWater().contains(l.getBlock().getBiome())) {
                final LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.ENDERMAN);
                e.setCanPickupItems(false);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        equipper.setSilentEquipment(e);
                    }
                }, 5L);
            }
        }
    }
}
