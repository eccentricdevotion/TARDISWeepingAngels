/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author eccentric_nz
 */
public class DalekRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final int maximum;
    private final MonsterEquipment equipper;

    public DalekRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.spawn_rate = plugin.getConfig().getInt("daleks.spawn_rate.how_many");
        this.maximum = plugin.getConfig().getInt("daleks.spawn_rate.max_per_world");
        this.equipper = new MonsterEquipment();
    }

    @Override
    public void run() {
        for (World w : plugin.getServer().getWorlds()) {
            // only configured worlds
            if (plugin.getConfig().getStringList("daleks.worlds").contains(w.getName())) {
                // get the current daleks
                List<Skeleton> daleks = new ArrayList<Skeleton>();
                Collection<Skeleton> disguised = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton d : disguised) {
                    // check if disguised
                    if (d.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) && DisguiseAPI.isDisguised(d)) {
                        daleks.add(d);
                    }
                }
                // count the current cybermen
                if (daleks.size() < maximum) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnDalek(w);
                    }
                }
            }
        }
    }

    private void spawnDalek(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            final LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.SKELETON);
            PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3);
            e.addPotionEffect(p);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    equipper.setDalekEquipment(e);
                }
            }, 5L);
        }
    }
}
