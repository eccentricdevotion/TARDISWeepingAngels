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
package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.UUID;

public class JudoonGuardRunnable implements Runnable {

    private final TardisWeepingAngelsPlugin plugin;

    public JudoonGuardRunnable(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getGuards().size() > 0) {
            for (UUID uuid : plugin.getGuards()) {
                Entity uuidEntity = Bukkit.getEntity(uuid);
                if (uuidEntity != null) {
                    for (Entity entity : uuidEntity.getNearbyEntities(8.0d, 8.0d, 8.0d)) {
                        if (entity instanceof Monster) {
                            Damageable damageable = (Damageable) entity;
                            double health = damageable.getHealth();
                            if (uuidEntity.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.JUDOON, PersistentDataType.INTEGER)) {
                                int ammo = uuidEntity.getPersistentDataContainer().get(TardisWeepingAngelsPlugin.JUDOON, PersistentDataType.INTEGER);
                                if (ammo > 0 && health > 0) {
                                    damageable.damage(plugin.getConfig().getDouble("judoon.damage"), uuidEntity);
                                    uuidEntity.getWorld().playSound(uuidEntity.getLocation(), "judoon_fire", 1.0f, 1.0f);
                                    Snowball snowball = ((LivingEntity) uuidEntity).launchProjectile(Snowball.class);
                                    Vector direction = damageable.getLocation().toVector().subtract(uuidEntity.getLocation().toVector());
                                    direction.normalize();
                                    Vector bulletVelocity = direction.multiply(3.0d);
                                    snowball.setVelocity(bulletVelocity);
                                    ammo -= 1;
                                    if (ammo >= 0) {
                                        ArmorStand stand = (ArmorStand) uuidEntity;
                                        stand.setCustomName("Ammunition: " + ammo);
                                        stand.setCustomNameVisible(true);
                                        uuidEntity.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.JUDOON, PersistentDataType.INTEGER, ammo);
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
