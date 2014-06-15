/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters;

import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author eccentric_nz
 */
public class CybermanRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final int maximum;
    private final MonsterEquipment equipper;

    public CybermanRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.spawn_rate = plugin.getConfig().getInt("cybermen.spawn_rate.how_many");
        this.maximum = plugin.getConfig().getInt("cybermen.spawn_rate.max_per_world");
        this.equipper = new MonsterEquipment();
    }

    @Override
    public void run() {
        for (World w : plugin.getServer().getWorlds()) {
            // only configured worlds
            if (plugin.getConfig().getStringList("cybermen.worlds").contains(w.getName())) {
                // get the current warriors
                List<Zombie> cyberarmy = new ArrayList<Zombie>();
                Collection<Zombie> zombies = w.getEntitiesByClass(Zombie.class);
                for (Zombie z : zombies) {
                    EntityEquipment ee = z.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                            cyberarmy.add(z);
                        }
                    }
                }
                // count the current cybermen
                if (cyberarmy.size() < maximum) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnCyberman(w);
                    }
                }
            }
        }
    }

    private void spawnCyberman(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            final LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.ZOMBIE);
            Zombie cyber = (Zombie) e;
            cyber.setVillager(false);
            PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3);
            cyber.addPotionEffect(p);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    equipper.setCyberEquipment(e, false);
                }
            }, 5L);
        }
    }
}
