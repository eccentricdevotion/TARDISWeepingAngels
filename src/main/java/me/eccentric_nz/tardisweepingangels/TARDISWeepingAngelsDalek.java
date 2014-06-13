/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.util.Vector;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelsDalek {

    private final TARDISWeepingAngels plugin;

    public TARDISWeepingAngelsDalek(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    public Projectile shootProjectile(Location target, LivingEntity le) {
        World w = le.getWorld();
        Location spawn = le.getLocation().clone();
        Location tl = target.clone();
        tl.setY(tl.getY() + 1);
        spawn.setY(spawn.getY() + 1);
        Vector v = tl.toVector().subtract(spawn.toVector()).normalize();
        for (int i = 0; i < 10; i++) {
            SmallFireball sn = (SmallFireball) w.spawnEntity(spawn, EntityType.SMALL_FIREBALL);
            sn.setVelocity(v);
            sn.setShooter(le);
            return sn;
        }
        return null;
    }
}
