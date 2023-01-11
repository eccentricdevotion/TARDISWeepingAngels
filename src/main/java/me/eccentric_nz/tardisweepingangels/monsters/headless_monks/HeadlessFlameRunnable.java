/*
 * Copyright (C) 2023 eccentric_nz
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
package me.eccentric_nz.tardisweepingangels.monsters.headless_monks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class HeadlessFlameRunnable implements Runnable {

    private final LivingEntity monk;

    public HeadlessFlameRunnable(LivingEntity monk) {
        this.monk = monk;
    }

    @Override
    public void run() {
        if (monk.isDead()) {
            Bukkit.getScheduler().cancelTask(monk.getPersistentDataContainer().get(TARDISWeepingAngels.FLAME_TASK, PersistentDataType.INTEGER));
            monk.getPersistentDataContainer().set(TARDISWeepingAngels.FLAME_TASK, PersistentDataType.INTEGER, -1);
            return;
        }
        // get centre location of entity
        Location location = monk.getLocation().clone().add(0.5, 0, 0.5);
        // get the direction of the entity
        Vector direction = location.getDirection();
        Vector scaledDir = direction.clone().multiply(0.65);
        // Adding scaled direction to location
        World world = location.getWorld();
        double x = scaledDir.getX() + location.getX();
        double y = scaledDir.getY() + location.getY();
        double z = scaledDir.getZ() + location.getZ();
        Location sword = new Location(world, x, y, z);
        // get start and end locations
        Location start = monk.getEyeLocation().clone().add(0, 1.25, 0);
        Location end = monk.getEyeLocation().clone().add(0, 2.0, 0);
        // spawn particles between the two locations
        spawnFlameAlongLine(start, end);
    }

    public void spawnFlameAlongLine(Location start, Location end) {
        double d = start.distance(end) / 10;
        for (int i = 0; i < 10; i++) {
            Location l = start.clone();
            Vector direction = end.toVector().subtract(start.toVector()).normalize();
            Vector v = direction.multiply(i * d);
            l.add(v.getX(), v.getY(), v.getZ());
            start.getWorld().spawnParticle(Particle.FLAME, l, 1, 0, 0, 0, 0.1, null, false);
        }
    }
}
