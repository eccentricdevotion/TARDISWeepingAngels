/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.WorldGuardChecker;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
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
public class ZygonRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final MonsterEquipment equipper;

    public ZygonRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
        equipper = new MonsterEquipment();
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("zygons.worlds." + name) > 0) {
                // get the current warriors
                List<Zombie> zygons = new ArrayList<>();
                Collection<Zombie> children = w.getEntitiesByClass(Zombie.class);
                children.forEach((c) -> {
                    EntityEquipment ee = c.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.GOLDEN_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Zygon")) {
                            zygons.add(c);
                        }
                    }
                });
                // count the current cybermen
                if (zygons.size() < plugin.getConfig().getInt("zygons.worlds." + name)) {
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
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            if (!plugin.getNotOnWater().contains(l.getBlock().getBiome()) && WorldGuardChecker.canSpawn(l)) {
                LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.ZOMBIE);
                e.setSilent(true);
                Zombie zygon = (Zombie) e;
                zygon.setBaby(false);
                PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                zygon.addPotionEffect(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    equipper.setZygonEquipment(e, false);
                    e.getPersistentDataContainer().set(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER, Monster.ZYGON.getPersist());
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIE, Monster.ZYGON, l));
                }, 5L);
            }
        }
    }
}
