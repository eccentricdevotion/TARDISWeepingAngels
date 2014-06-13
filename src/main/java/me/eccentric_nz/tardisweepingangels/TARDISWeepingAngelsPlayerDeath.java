/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
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
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelsPlayerDeath implements Listener {

    private final TARDISWeepingAngels plugin;

    public TARDISWeepingAngelsPlayerDeath(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        EntityDamageEvent damage = event.getEntity().getLastDamageCause();
        if (damage != null && damage.getCause().equals(DamageCause.ENTITY_ATTACK)) {
            Entity attacker = (((EntityDamageByEntityEvent) damage).getDamager());
            if (attacker instanceof Zombie) {
                EntityEquipment ee = ((LivingEntity) attacker).getEquipment();
                ItemStack is = ee.getHelmet();
                if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                    String dn = is.getItemMeta().getDisplayName();
                    if (dn.startsWith("Cyberman")) {
                        String what_happened = (plugin.getConfig().getBoolean("cybermen.can_upgrade")) ? "upgraded" : "slain";
                        String name = event.getEntity().getName();
                        event.setDeathMessage(name + " was " + what_happened + " by a Cyberman");
                        return;
                    }
                    if (dn.startsWith("Empty Child")) {
                        String name = event.getEntity().getName();
                        event.setDeathMessage(name + " was slain by an Empty Child");
                        return;
                    }
                    if (dn.startsWith("Zygon")) {
                        String name = event.getEntity().getName();
                        event.setDeathMessage(name + " was slain by a Zygon");
                        return;
                    }
                }
            }
            if (attacker instanceof Skeleton) {
                EntityEquipment ee = ((LivingEntity) attacker).getEquipment();
                if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                    String name = event.getEntity().getName();
                    event.setDeathMessage(name + " was slain by a Weeping Angel");
                    return;
                }
                if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                    ItemStack is = ee.getHelmet();
                    if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Silurian")) {
                        String name = event.getEntity().getName();
                        event.setDeathMessage(name + " was slain by a Silurian");
                        return;
                    }
                }
                Skeleton dalek = (Skeleton) event.getEntity();
                if (dalek.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) && DisguiseAPI.isDisguised(dalek)) {
                    String name = event.getEntity().getName();
                    event.setDeathMessage(name + " was slain by a Dalek");
                    return;
                }
            }
            if (attacker instanceof PigZombie) {
                EntityEquipment ee = ((LivingEntity) attacker).getEquipment();
                if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                    String name = event.getEntity().getName();
                    event.setDeathMessage(name + " was slain by an Ice Warrior");
                }
            }
        }
    }
}
