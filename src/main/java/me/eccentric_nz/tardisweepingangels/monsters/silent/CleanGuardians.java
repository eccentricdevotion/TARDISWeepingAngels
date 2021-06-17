/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.tardisweepingangels.monsters.silent;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.WorldProcessor;
import org.bukkit.entity.Guardian;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

/**
 * @author eccentric_nz
 */
public class CleanGuardians implements Runnable {

    private final TardisWeepingAngelsPlugin plugin;

    public CleanGuardians(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((world) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(world.getName());
            if (plugin.getConfig().getInt("silent.worlds." + name) > 0) {
                Collection<Guardian> guardians = world.getEntitiesByClass(Guardian.class);
                guardians.forEach((guardian) -> {
                    // does it have invisibility but not riding an Enderman
                    if (guardian.hasPotionEffect(PotionEffectType.INVISIBILITY) && guardian.getVehicle() == null) {
                        guardian.remove();
                    }
                });
            }
        });
    }
}
