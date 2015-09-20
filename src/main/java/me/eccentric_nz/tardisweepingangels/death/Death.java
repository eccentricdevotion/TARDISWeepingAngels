/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.death;

import java.util.ArrayList;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
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
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author eccentric_nz
 */
public class Death implements Listener {

    private final TARDISWeepingAngels plugin;
    private final List<Material> angel_drops = new ArrayList<Material>();
    private final List<Material> cyber_drops = new ArrayList<Material>();
    private final List<Material> dalek_drops = new ArrayList<Material>();
    private final List<Material> empty_drops = new ArrayList<Material>();
    private final List<Material> ice_drops = new ArrayList<Material>();
    private final List<Material> silurian_drops = new ArrayList<Material>();
    private final List<Material> sontaran_drops = new ArrayList<Material>();
    private final List<Material> vashta_drops = new ArrayList<Material>();
    private final List<Material> zygon_drops = new ArrayList<Material>();

    public Death(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        for (String a : plugin.getConfig().getStringList("angels.drops")) {
            this.angel_drops.add(Material.valueOf(a));
        }
        for (String c : plugin.getConfig().getStringList("cybermen.drops")) {
            this.cyber_drops.add(Material.valueOf(c));
        }
        for (String d : plugin.getConfig().getStringList("daleks.drops")) {
            this.dalek_drops.add(Material.valueOf(d));
        }
        for (String e : plugin.getConfig().getStringList("empty_child.drops")) {
            this.empty_drops.add(Material.valueOf(e));
        }
        for (String i : plugin.getConfig().getStringList("ice_warriors.drops")) {
            this.ice_drops.add(Material.valueOf(i));
        }
        for (String o : plugin.getConfig().getStringList("sontarans.drops")) {
            this.sontaran_drops.add(Material.valueOf(o));
        }
        for (String s : plugin.getConfig().getStringList("silurians.drops")) {
            this.silurian_drops.add(Material.valueOf(s));
        }
        for (String v : plugin.getConfig().getStringList("vashta_nerada.drops")) {
            this.vashta_drops.add(Material.valueOf(v));
        }
        for (String z : plugin.getConfig().getStringList("zygons.drops")) {
            this.zygon_drops.add(Material.valueOf(z));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType().equals(EntityType.SKELETON)) {
            EntityEquipment ee = event.getEntity().getEquipment();
            if (ee.getItemInHand().getType().equals(Material.BARRIER) || ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                event.getDrops().clear();
                ItemStack stack;
                if (plugin.getRandom().nextInt(100) < 3) {
                    stack = new ItemStack(Material.SKULL_ITEM, 1);
                } else {
                    stack = new ItemStack(angel_drops.get(plugin.getRandom().nextInt(angel_drops.size())), plugin.getRandom().nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
            }
            if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                ItemStack is = ee.getHelmet();
                if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Silurian")) {
                    event.getDrops().clear();
                    ItemStack stack = new ItemStack(silurian_drops.get(plugin.getRandom().nextInt(silurian_drops.size())), plugin.getRandom().nextInt(2) + 1);
                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                    return;
                }
            }
            Skeleton dalek = (Skeleton) event.getEntity();
            if (dalek.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) && DisguiseAPI.isDisguised(dalek)) {
                event.getDrops().clear();
                ItemStack stack = new ItemStack(dalek_drops.get(plugin.getRandom().nextInt(dalek_drops.size())), 1);
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
            }
        }
        if (event.getEntityType().equals(EntityType.PIG_ZOMBIE)) {
            EntityEquipment ee = event.getEntity().getEquipment();
            if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                ItemStack is = ee.getHelmet();
                if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Ice")) {
                    event.getDrops().clear();
                    ItemStack stack = new ItemStack(ice_drops.get(plugin.getRandom().nextInt(ice_drops.size())), plugin.getRandom().nextInt(1) + 1);
                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                    return;
                }
            }
        }
        if (event.getEntityType().equals(EntityType.ZOMBIE)) {
            EntityEquipment ee = event.getEntity().getEquipment();
            if (ee.getHelmet().getType().equals(Material.IRON_HELMET) || ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                ItemStack is = ee.getHelmet();
                if (is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                    ItemStack stack;
                    if (is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                        event.getDrops().clear();
                        if (plugin.getRandom().nextInt(100) < 3) {
                            stack = new ItemStack(Material.IRON_INGOT, 1);
                        } else {
                            stack = new ItemStack(cyber_drops.get(plugin.getRandom().nextInt(cyber_drops.size())), 1);
                        }
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                        return;
                    }
                    if (is.getItemMeta().getDisplayName().startsWith("Empty Child")) {
                        event.getDrops().clear();
                        if (plugin.getRandom().nextInt(100) < 3) {
                            stack = new ItemStack(Material.POTION, 1, (short) 8197);
                        } else {
                            stack = new ItemStack(empty_drops.get(plugin.getRandom().nextInt(empty_drops.size())), plugin.getRandom().nextInt(1) + 1);
                        }
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                        return;
                    }
                    if (is.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                        event.getDrops().clear();
                        if (plugin.getRandom().nextInt(100) < 3) {
                            stack = new ItemStack(Material.POISONOUS_POTATO, 1);
                        } else {
                            stack = new ItemStack(sontaran_drops.get(plugin.getRandom().nextInt(sontaran_drops.size())), 1);
                        }
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                        return;
                    }
                    if (is.getItemMeta().getDisplayName().startsWith("Vashta")) {
                        event.getDrops().clear();
                        stack = new ItemStack(vashta_drops.get(plugin.getRandom().nextInt(vashta_drops.size())), plugin.getRandom().nextInt(2) + 1);
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);

                    }
                    if (is.getItemMeta().getDisplayName().startsWith("Zygon")) {
                        event.getDrops().clear();
                        stack = new ItemStack(zygon_drops.get(plugin.getRandom().nextInt(zygon_drops.size())), plugin.getRandom().nextInt(1) + 1);
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                        return;
                    }
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
                    if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                        String dn = is.getItemMeta().getDisplayName();
                        if (dn.startsWith("Cyberman")) {
                            Location l = event.getEntity().getLocation();
                            LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
                            new MonsterEquipment().setCyberEquipment(e, false);
                            if (event.getEntity() instanceof Player) {
                                String name = ((Player) event.getEntity()).getName();
                                e.setCustomName(name);
                                e.setCustomNameVisible(true);
                            }
                            return;
                        }
                        if (dn.startsWith("Empty Child")) {
                            if (event.getEntity() instanceof Player) {
                                Player p = (Player) event.getEntity();
                                plugin.getEmpty().add(p.getUniqueId());
                            }
                        }
                    }
                }
            }
        }
        if (event.getEntityType().equals(EntityType.ENDERMAN)) {
            Entity enderman = event.getEntity();
            if (enderman.getPassenger() != null && enderman.getPassenger().getType().equals(EntityType.GUARDIAN)) {
                // remove the guardian as well
                Entity guardian = enderman.getPassenger();
                guardian.remove();
                event.getDrops().clear();
            }
        }
    }
}
