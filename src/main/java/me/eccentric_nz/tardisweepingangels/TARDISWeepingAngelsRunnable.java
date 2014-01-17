/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelsRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final int maximum;
    private final TARDISWeepingAngelEquipment equipper;

    public TARDISWeepingAngelsRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
        this.maximum = plugin.getConfig().getInt("spawn_rate.max_per_world");
        this.equipper = new TARDISWeepingAngelEquipment();
    }

    @Override
    public void run() {
        for (World w : plugin.getServer().getWorlds()) {
            // only configured worlds
            if (plugin.getConfig().getStringList("worlds").contains(w.getName())) {
                long time = w.getTime();
                // only spawn at night - times according to http://minecraft.gamepedia.com/Day-night_cycle
                if (time > 9625 && time < 22812) {
                    // get the current angels
                    List<Skeleton> angels = new ArrayList<Skeleton>();
                    Collection<Skeleton> skellies = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton s : skellies) {
                        EntityEquipment ee = s.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                            angels.add(s);
                        }
                    }
                    // count the current angels
                    if (angels.size() < maximum) {
                        // if less than maximum, spawn some more
                        for (int i = 0; i < spawn_rate; i++) {
                            spawnAngel(w);
                        }
                    }
                }
            }
        }
    }

    private void spawnAngel(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
        int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
        int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
        int y = w.getHighestBlockYAt(x, z);
        Location l = new Location(w, x, y + 1, z);
        LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.SKELETON);
        equipper.setEquipment(e);
    }
}
