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
package me.eccentric_nz.tardisweepingangels.death;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;

/**
 * @author eccentric_nz
 */
public class PlayerDeath implements Listener {

    private final TardisWeepingAngelsPlugin plugin;

    public PlayerDeath(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        EntityDamageEvent damage = event.getEntity().getLastDamageCause();
        if (damage != null) {
            if (damage instanceof EntityDamageByEntityEvent damageByEntity) {
                Entity attacker = damageByEntity.getDamager();
                if (damage.getCause().equals(DamageCause.ENTITY_ATTACK)) {
                    PersistentDataContainer attackerPersistentDataContainer = attacker.getPersistentDataContainer();
                    String name = event.getEntity().getName();
                    if (attacker instanceof Zombie) {
                        if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER)) {
                            String whatHappened = (plugin.getConfig().getBoolean("cybermen.can_upgrade")) ? "upgraded" : "slain";
                            event.setDeathMessage(name + " was " + whatHappened + " by a Cyberman");
                            return;
                        }
                        if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was slain by an Empty Child");
                            return;
                        }
                        if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.zygon, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was slain by a Zygon");
                            return;
                        }
                        if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.sontaran, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was slain by a Sontaran");
                            return;
                        }
                        if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was eaten by a Vashta Nerada");
                            return;
                        }
                    }
                    if (attacker instanceof Enderman) {
                        if (!attacker.getPassengers().isEmpty()) {
                            Entity passenger = attacker.getPassengers().get(0);
                            if (passenger != null && passenger.getType().equals(EntityType.GUARDIAN)) {
                                event.setDeathMessage(name + " was slain by a Silent");
                                return;
                            }
                        }
                    }
                    if (attacker instanceof Guardian) {
                        Entity silent = attacker.getVehicle();
                        if (silent != null && silent.getType().equals(EntityType.ENDERMAN)) {
                            event.setDeathMessage(name + " was slain by a Silent");
                            return;
                        }
                    }
                    if (attacker instanceof Bee) {
                        if (!attacker.getPassengers().isEmpty()) {
                            Entity passenger = attacker.getPassengers().get(0);
                            if (passenger != null && passenger.getType().equals(EntityType.ARMOR_STAND)) {
                                event.setDeathMessage(name + " was slain by a Toclafane");
                                return;
                            }
                        }
                    }
                    if (attacker instanceof PigZombie) {
                        if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was slain by an Ice Warrior");
                            return;
                        }
                        if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.strax, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was slain by a very angry Sontaran butler called Strax");
                            return;
                        }
                        if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.hath, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was killed by a Hath blaster rifle");
                            return;
                        }
                    }
                    if (attacker instanceof Skeleton) {
                        if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was slain by a Weeping Angel");
                            return;
                        }
                        if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was slain by a Silurian");
                            return;
                        }
                    }
                }
                if (attacker.getType().equals(EntityType.ARROW)) {
                    Projectile arrow = (Arrow) attacker;
                    ProjectileSource source = arrow.getShooter();
                    if (source instanceof Skeleton skeleton) {
                        PersistentDataContainer spdc = skeleton.getPersistentDataContainer();
                        String name = event.getEntity().getName();
                        if (spdc.has(TardisWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was slain by a Dalek");
                            return;
                        }
                        if (spdc.has(TardisWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                            event.setDeathMessage(name + " was slain by a Silurian");
                        }
                    }
                }
            } else if (damage.getCause().equals(DamageCause.BLOCK_EXPLOSION)) {
                if (event.getDeathMessage().contains("Bee")) {
                    event.setDeathMessage(event.getEntity().getName() + " was slain by a Toclafane");
                }
            }
        }
    }
}
