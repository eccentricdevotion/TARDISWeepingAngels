/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.sontarans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Config;
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
public class SontaranRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final MonsterEquipment equipper;

    public SontaranRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
        this.equipper = new MonsterEquipment();
    }

    @Override
    public void run() {
        for (World w : plugin.getServer().getWorlds()) {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("sontarans.worlds." + name) > 0) {
                long time = w.getTime();
                // only spawn in day - times according to http://minecraft.gamepedia.com/Day-night_cycle
                if ((time > 0 && time < 13187) || time > 22812) {
                    // get the current warriors
                    List<Zombie> sontarans = new ArrayList<Zombie>();
                    Collection<Zombie> potatoes = w.getEntitiesByClass(Zombie.class);
                    for (Zombie pz : potatoes) {
                        EntityEquipment ee = pz.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                                sontarans.add(pz);
                            }
                        }
                    }
                    // count the current sontarans
                    if (sontarans.size() < plugin.getConfig().getInt("sontarans.worlds." + name)) {
                        // if less than maximum, spawn some more
                        for (int i = 0; i < spawn_rate; i++) {
                            spawnSontaran(w);
                        }
                    }
                }
            }
        }
    }

    private void spawnSontaran(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            if (!plugin.getNotOnWater().contains(l.getBlock().getBiome())) {
                final LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.ZOMBIE);
                Zombie sontaran = (Zombie) e;
                sontaran.setVillager(false);
                PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3);
                sontaran.addPotionEffect(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        equipper.setSontaranEquipment(e, false);
                    }
                }, 5L);
            }
        }
    }
}
