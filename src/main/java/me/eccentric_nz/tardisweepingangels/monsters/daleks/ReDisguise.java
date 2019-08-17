/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

/**
 * @author eccentric_nz
 */
public class ReDisguise implements Runnable {

    private final TARDISWeepingAngels plugin;

    public ReDisguise(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("daleks.worlds." + name) > 0 || name.equals("Skaro")) {
                // get the current daleks
                Collection<Skeleton> daleks = w.getEntitiesByClass(Skeleton.class);
                daleks.forEach((d) -> {
                    // does it have a helmet with a display name
                    EntityEquipment ee = d.getEquipment();
                    ItemStack is = ee.getHelmet();
                    if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Dalek")) {
                        if (plugin.isLibsEnabled() && !DalekDisguiseLibs.isDisguised(d)) {
                            DalekDisguiseLibs.disguise(d);
                        } else if (!DalekDisguise.isDisguised(d)) {
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> DalekDisguise.redisguise(d, w), 1L);
                        }
                    }
                });
            }
            if (plugin.getConfig().getInt("silent.worlds." + name) > 0) {
                Collection<Guardian> guardians = w.getEntitiesByClass(Guardian.class);
                guardians.forEach((g) -> {
                    // does it have invisibilty but not riding an Enderman
                    if (g.hasPotionEffect(PotionEffectType.INVISIBILITY) && g.getVehicle() == null) {
                        g.remove();
                    }
                });
            }
        });
    }
}
