/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.silurians;

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
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

/**
 * @author eccentric_nz
 */
public class SilurianRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final MonsterEquipment equipper;

    public SilurianRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
        equipper = new MonsterEquipment();
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("silurians.worlds." + name) > 0) {
                // check the world generator
                if (w.getGenerator() != null) {
                    plugin.getConfig().set("silurians.worlds." + name, 0);
                    plugin.saveConfig();
                    plugin.getServer().getLogger().log(Level.WARNING, "TARDISWeepingAngels cannot safely spawn Silurians in custom worlds!");
                } else {
                    // get the current silurian count
                    List<Skeleton> silurians = new ArrayList<>();
                    Collection<Skeleton> skeletons = w.getEntitiesByClass(Skeleton.class);
                    skeletons.forEach((s) -> {
                        EntityEquipment ee = s.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.GOLDEN_HELMET)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Silurian")) {
                                silurians.add(s);
                            }
                        }
                    });
                    if (silurians.size() < plugin.getConfig().getInt("silurians.worlds." + name)) {
                        // if less than maximum, spawn some more
                        for (int i = 0; i < spawn_rate; i++) {
                            spawnSilurian(w);
                        }
                    }
                }
            }
        });
    }

    private void spawnSilurian(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            Location search = CaveFinder.searchCave(l);
            Location cave = ((search == null)) ? l : search;
            LivingEntity e = (LivingEntity) w.spawnEntity(cave, EntityType.SKELETON);
            e.setSilent(true);
            PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
            e.addPotionEffect(p);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                equipper.setSilurianEquipment(e, false);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.SKELETON, Monster.SILURIAN, cave));
            }, 5L);
        }
    }
}
