/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.silent;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.WorldProcessor;
import org.bukkit.entity.Guardian;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

/**
 * @author eccentric_nz
 */
public class CleanGuardians implements Runnable {

    private final TARDISWeepingAngels plugin;

    public CleanGuardians(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("silent.worlds." + name) > 0) {
                Collection<Guardian> guardians = w.getEntitiesByClass(Guardian.class);
                guardians.forEach((g) -> {
                    // does it have invisibility but not riding an Enderman
                    if (g.hasPotionEffect(PotionEffectType.INVISIBILITY) && g.getVehicle() == null) {
                        g.remove();
                    }
                });
            }
        });
    }
}
