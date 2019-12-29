/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.death;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
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

    private final TARDISWeepingAngels plugin;

    public PlayerDeath(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        EntityDamageEvent damage = event.getEntity().getLastDamageCause();
        if (damage != null && damage instanceof EntityDamageByEntityEvent) {
            Entity attacker = (((EntityDamageByEntityEvent) damage).getDamager());
            if (damage.getCause().equals(DamageCause.ENTITY_ATTACK)) {
                PersistentDataContainer pdc = attacker.getPersistentDataContainer();
                String name = event.getEntity().getName();
                if (attacker instanceof Zombie) {
                    if (pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)) {
                        String what_happened = (plugin.getConfig().getBoolean("cybermen.can_upgrade")) ? "upgraded" : "slain";
                        event.setDeathMessage(name + " was " + what_happened + " by a Cyberman");
                        return;
                    }
                    if (pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                        event.setDeathMessage(name + " was slain by an Empty Child");
                        return;
                    }
                    if (pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                        event.setDeathMessage(name + " was slain by a Zygon");
                        return;
                    }
                    if (pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                        event.setDeathMessage(name + " was slain by a Sontaran");
                        return;
                    }
                    if (pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
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
                if (attacker instanceof PigZombie) {
                    if (pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)) {
                        event.setDeathMessage(name + " was slain by an Ice Warrior");
                        return;
                    }
                    if (pdc.has(TARDISWeepingAngels.STRAX, PersistentDataType.INTEGER)) {
                        event.setDeathMessage(name + " was slain by a very angry Sontaran butler called Strax");
                        return;
                    }
                }
                if (attacker instanceof Skeleton) {
                    if (pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                        event.setDeathMessage(name + " was slain by a Weeping Angel");
                        return;
                    }
                    if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
                        event.setDeathMessage(name + " was slain by a Silurian");
                        return;
                    }
                }
            }
            if (attacker.getType().equals(EntityType.ARROW)) {
                Projectile arrow = (Arrow) attacker;
                ProjectileSource source = arrow.getShooter();
                if (source instanceof Skeleton) {
                    Skeleton skeleton = (Skeleton) source;
                    PersistentDataContainer spdc = skeleton.getPersistentDataContainer();
                    String name = event.getEntity().getName();
                    if (spdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                        event.setDeathMessage(name + " was slain by a Dalek");
                        return;
                    }
                    if (spdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
                        event.setDeathMessage(name + " was slain by a Silurian");
                    }
                }
            }
        }
    }
}
