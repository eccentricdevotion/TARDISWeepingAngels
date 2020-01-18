package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.UUID;

public class JudoonGuardRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;

    public JudoonGuardRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getGuards().size() > 0) {
            for (UUID uuid : plugin.getGuards()) {
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null) {
                    for (Entity e : entity.getNearbyEntities(8.0d, 8.0d, 8.0d)) {
                        if (e instanceof Monster) {
                            Damageable damageable = (Damageable) e;
                            double health = damageable.getHealth();
                            if (entity.getPersistentDataContainer().has(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER)) {
                                int ammo = entity.getPersistentDataContainer().get(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER);
                                if (ammo > 0 && health > 0) {
                                    damageable.damage(plugin.getConfig().getDouble("judoon.damage"), entity);
                                    entity.getWorld().playSound(entity.getLocation(), "judoon_fire", 1.0f, 1.0f);
                                    Snowball snowball = ((LivingEntity) entity).launchProjectile(Snowball.class);
                                    Vector direction = damageable.getLocation().toVector().subtract(entity.getLocation().toVector());
                                    direction.normalize();
                                    Vector bulletVelocity = direction.multiply(3.0d);
                                    snowball.setVelocity(bulletVelocity);
                                    ammo -= 1;
                                    if (ammo >= 0) {
                                        ArmorStand stand = (ArmorStand) entity;
                                        stand.setCustomName("Ammunition: " + ammo);
                                        stand.setCustomNameVisible(true);
                                        entity.getPersistentDataContainer().set(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER, ammo);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
