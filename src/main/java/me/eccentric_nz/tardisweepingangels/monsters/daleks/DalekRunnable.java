/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.WorldGuardChecker;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author eccentric_nz
 */
public class DalekRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final MonsterEquipment equipper;

    public DalekRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
        equipper = new MonsterEquipment();
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("daleks.worlds." + name) > 0) {
                // get the current daleks
                List<Skeleton> daleks = new ArrayList<>();
                Collection<Skeleton> disguised = w.getEntitiesByClass(Skeleton.class);
                disguised.forEach((d) -> {
                    // does it have a helmet with a display name
                    EntityEquipment ee = d.getEquipment();
                    ItemStack is = ee.getHelmet();
                    if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Dalek")) {
                        daleks.add(d);
                    }
                });
                // count the current daleks
                if (daleks.size() < plugin.getConfig().getInt("daleks.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnDalek(w);
                    }
                }
            }
        });
    }

    private void spawnDalek(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            if (!plugin.getNotOnWater().contains(l.getBlock().getBiome()) && WorldGuardChecker.canSpawn(l)) {
                LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.SKELETON);
                e.setSilent(true);
                PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                e.addPotionEffect(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    equipper.setDalekEquipment(e);
                    e.getPersistentDataContainer().set(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER, Monster.DALEK.getPersist());
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.SKELETON, Monster.DALEK, l));
                }, 5L);
            }
        }
    }
}
