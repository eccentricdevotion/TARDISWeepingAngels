/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelsDeath implements Listener {

    private final TARDISWeepingAngels plugin;
    private final List<Material> angel_drops = new ArrayList<Material>();
    private final List<Material> ice_drops = new ArrayList<Material>();
    private final List<Material> cyber_drops = new ArrayList<Material>();

    public TARDISWeepingAngelsDeath(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        for (String s : plugin.getConfig().getStringList("angels.drops")) {
            this.angel_drops.add(Material.valueOf(s));
        }
        for (String i : plugin.getConfig().getStringList("ice_warriors.drops")) {
            this.ice_drops.add(Material.valueOf(i));
        }
        for (String c : plugin.getConfig().getStringList("cybermen.drops")) {
            this.cyber_drops.add(Material.valueOf(c));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAngelDeath(EntityDeathEvent event) {
        if (event.getEntityType().equals(EntityType.SKELETON)) {
            EntityEquipment ee = event.getEntity().getEquipment();
            if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                event.getDrops().clear();
                ItemStack stack;
                if (plugin.getRandom().nextInt(100) < 3) {
                    stack = new ItemStack(Material.SKULL_ITEM, 1);
                } else {
                    stack = new ItemStack(angel_drops.get(plugin.getRandom().nextInt(angel_drops.size())), plugin.getRandom().nextInt(3) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
            }
        }
        if (event.getEntityType().equals(EntityType.PIG_ZOMBIE)) {
            EntityEquipment ee = event.getEntity().getEquipment();
            if (ee.getHelmet().getType().equals(Material.IRON_HELMET) || (ee.getHelmet().getType().equals(Material.LEATHER_HELMET) && plugin.getConfig().getBoolean("always_use_leather"))) {
                ItemStack is = ee.getHelmet();
                if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Ice")) {
                    event.getDrops().clear();
                    ItemStack stack = new ItemStack(ice_drops.get(plugin.getRandom().nextInt(ice_drops.size())), plugin.getRandom().nextInt(3) + 1);
                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                }
            }
        }
        if (event.getEntityType().equals(EntityType.ZOMBIE)) {
            EntityEquipment ee = event.getEntity().getEquipment();
            if (ee.getHelmet().getType().equals(Material.IRON_HELMET) || (ee.getHelmet().getType().equals(Material.LEATHER_HELMET) && plugin.getConfig().getBoolean("always_use_leather"))) {
                ItemStack is = ee.getHelmet();
                if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                    event.getDrops().clear();
                    ItemStack stack;
                    if (plugin.getRandom().nextInt(100) < 3) {
                        stack = new ItemStack(Material.IRON_INGOT, 1);
                    } else {
                        stack = new ItemStack(cyber_drops.get(plugin.getRandom().nextInt(cyber_drops.size())), 1);
                    }
                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                }
            }
        }
        if (event.getEntityType().equals(EntityType.VILLAGER) || event.getEntityType().equals(EntityType.PLAYER)) {
            if (!plugin.getConfig().getBoolean("cybermen.can_upgrade")) {
                return;
            }
            EntityDamageEvent damage = event.getEntity().getLastDamageCause();
            if (damage != null && damage.getCause().equals(DamageCause.ENTITY_ATTACK)) {
                Entity attacker = (((EntityDamageByEntityEvent) damage).getDamager());
                if (attacker instanceof Zombie) {
                    EntityEquipment ee = ((LivingEntity) attacker).getEquipment();
                    ItemStack is = ee.getHelmet();
                    if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                        plugin.debug("A cyberman upgraded a " + event.getEntityType().toString() + "!");
                        Location l = event.getEntity().getLocation();
                        LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
                        new TARDISWeepingAngelEquipment().setCyberEquipment(e, false);
                        if (event.getEntity() instanceof Player) {
                            String name = ((Player) event.getEntity()).getName();
                            e.setCustomName(name);
                            e.setCustomNameVisible(true);
                        }
                    }
                }
            }
        }
    }
}
