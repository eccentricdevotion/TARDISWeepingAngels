/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.death;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

/**
 *
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
                String name = event.getEntity().getName();
                if (attacker instanceof Zombie) {
                    EntityEquipment ee = ((LivingEntity) attacker).getEquipment();
                    ItemStack is = ee.getHelmet();
                    if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                        String dn = is.getItemMeta().getDisplayName();
                        if (dn.startsWith("Cyberman")) {
                            String what_happened = (plugin.getConfig().getBoolean("cybermen.can_upgrade")) ? "upgraded" : "slain";
                            event.setDeathMessage(name + " was " + what_happened + " by a Cyberman");
                            return;
                        }
                        if (dn.startsWith("Empty Child")) {
                            event.setDeathMessage(name + " was slain by an Empty Child");
                            return;
                        }
                        if (dn.startsWith("Zygon")) {
                            event.setDeathMessage(name + " was slain by a Zygon");
                            return;
                        }
                        if (dn.startsWith("Sontaran")) {
                            event.setDeathMessage(name + " was slain by a Sontaran");
                            return;
                        }
                    }
                }
                if (attacker instanceof PigZombie) {
                    EntityEquipment ee = ((LivingEntity) attacker).getEquipment();
                    if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                        event.setDeathMessage(name + " was slain by an Ice Warrior");
                        return;
                    }
                    if (ee.getHelmet().getType().equals(Material.CHAINMAIL_HELMET) && ((PigZombie) attacker).getCustomName().equals("Strax")) {
                        event.setDeathMessage(name + " was slain by a very angry Sontaran butler called Strax");
                        return;
                    }
                }
            }
            if (attacker.getType().equals(EntityType.ARROW)) {
                Projectile arrow = (Arrow) attacker;
                ProjectileSource source = arrow.getShooter();
                if (source instanceof Skeleton) {
                    Skeleton skeleton = (Skeleton) source;
                    String name = event.getEntity().getName();
                    if (DisguiseAPI.isDisguised(skeleton)) {
                        event.setDeathMessage(name + " was slain by a Dalek");
                        return;
                    }
                    EntityEquipment ee = skeleton.getEquipment();
                    ItemStack is = ee.getHelmet();
                    if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                        String dn = is.getItemMeta().getDisplayName();
                        if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                            event.setDeathMessage(name + " was slain by a Weeping Angel");
                            return;
                        }
                        if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && dn.startsWith("Silurian")) {
                                event.setDeathMessage(name + " was slain by a Silurian");
                            }
                        }
                    }
                }
            }
        }
    }
}
