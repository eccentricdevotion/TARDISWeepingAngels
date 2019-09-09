/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Skeleton;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
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
                    PersistentDataContainer pdc = d.getPersistentDataContainer();
                    if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                        if (d.getEquipment().getHelmet() == null) {
                            TARDISWeepingAngels.getEqipper().setDalekEquipment(d, false);
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
