/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
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
    private final MonsterEquipment equipper;

    public CybermanRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
        this.equipper = new MonsterEquipment();
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("cybermen.worlds." + name) > 0) {
                // get the current warriors
                List<Zombie> cyberarmy = new ArrayList<>();
                Collection<Zombie> zombies = w.getEntitiesByClass(Zombie.class);
                zombies.forEach((z) -> {
                    EntityEquipment ee = z.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                            cyberarmy.add(z);
                        }
                    }
                });
                // count the current cybermen
                if (cyberarmy.size() < plugin.getConfig().getInt("cybermen.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnCyberman(w);
                    }
                }
            }
        });
    }

    private void spawnCyberman(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            final Location l = new Location(w, x, y + 1, z);
            if (!plugin.getNotOnWater().contains(l.getBlock().getBiome())) {
                final LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.ZOMBIE);
                e.setSilent(true);
                Zombie cyber = (Zombie) e;
                //cyber.setVillager(false);
                //cyber.setVillagerProfession(null);
                cyber.setBaby(false);
                PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                cyber.addPotionEffect(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    equipper.setCyberEquipment(e, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIE, Monster.CYBERMAN, l));
                }, 5L);
            }
        }
    }
}
