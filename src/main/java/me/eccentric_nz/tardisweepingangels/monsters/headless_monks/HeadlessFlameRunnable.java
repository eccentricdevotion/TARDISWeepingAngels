package me.eccentric_nz.tardisweepingangels.monsters.headless_monks;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class HeadlessFlameRunnable implements Runnable {

    private final LivingEntity monk;

    public HeadlessFlameRunnable(LivingEntity monk) {
        this.monk = monk;
    }

    @Override
    public void run() {
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
