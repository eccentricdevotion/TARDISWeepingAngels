/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.death;

import java.util.ArrayList;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.libraryaddict.disguise.DisguiseAPI;
import net.citizensnpcs.api.CitizensAPI;
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
    private final List<Material> angel_drops = new ArrayList<>();
    private final List<Material> cyber_drops = new ArrayList<>();
    private final List<Material> dalek_drops = new ArrayList<>();
    private final List<Material> empty_drops = new ArrayList<>();
    private final List<Material> silent_drops = new ArrayList<>();
    private final List<Material> ice_drops = new ArrayList<>();
    private final List<Material> silurian_drops = new ArrayList<>();
    private final List<Material> sontaran_drops = new ArrayList<>();
    private final List<Material> vashta_drops = new ArrayList<>();
    private final List<Material> zygon_drops = new ArrayList<>();

    public Death(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        plugin.getConfig().getStringList("angels.drops").forEach((a) -> {
            this.angel_drops.add(Material.valueOf(a));
        });
        plugin.getConfig().getStringList("cybermen.drops").forEach((c) -> {
            this.cyber_drops.add(Material.valueOf(c));
        });
        plugin.getConfig().getStringList("daleks.drops").forEach((d) -> {
            this.dalek_drops.add(Material.valueOf(d));
        });
        plugin.getConfig().getStringList("empty_child.drops").forEach((e) -> {
            this.empty_drops.add(Material.valueOf(e));
        });
        plugin.getConfig().getStringList("ice_warriors.drops").forEach((i) -> {
            this.ice_drops.add(Material.valueOf(i));
        });
        plugin.getConfig().getStringList("sontarans.drops").forEach((o) -> {
            this.sontaran_drops.add(Material.valueOf(o));
        });
        plugin.getConfig().getStringList("silent.drops").forEach((m) -> {
            this.silent_drops.add(Material.valueOf(m));
        });
        plugin.getConfig().getStringList("silurians.drops").forEach((s) -> {
            this.silurian_drops.add(Material.valueOf(s));
        });
        plugin.getConfig().getStringList("vashta_nerada.drops").forEach((v) -> {
            this.vashta_drops.add(Material.valueOf(v));
        });
        plugin.getConfig().getStringList("zygons.drops").forEach((z) -> {
            this.zygon_drops.add(Material.valueOf(z));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType().equals(EntityType.SKELETON)) {
            EntityEquipment ee = event.getEntity().getEquipment();
            if (ee.getItemInMainHand().getType().equals(Material.BARRIER) || ee.getHelmet().getType().equals(Material.LILY_PAD)) {
                event.getDrops().clear();
                ItemStack stack;
                if (plugin.getRandom().nextInt(100) < 3) {
                    stack = new ItemStack(Material.SKELETON_SKULL, 1);
                } else {
                    stack = new ItemStack(angel_drops.get(plugin.getRandom().nextInt(angel_drops.size())), plugin.getRandom().nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
            }
            if (ee.getHelmet().getType().equals(Material.GOLDEN_HELMET)) {
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
            if (ee.getHelmet().getType().equals(Material.IRON_HELMET) || ee.getHelmet().getType().equals(Material.GOLDEN_HELMET)) {
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
                            stack = new ItemStack(Material.MILK_BUCKET, 1);
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
            if (plugin.isCitizensEnabled() && CitizensAPI.getNPCRegistry().isNPC(event.getEntity())) {
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
                            e.setSilent(true);
                            new MonsterEquipment().setCyberEquipment(e, false);
                            plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIE, Monster.CYBERMAN, l));
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
            if (!enderman.getPassengers().isEmpty() && enderman.getPassengers().get(0) != null && enderman.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
                // remove the guardian as well
                Entity guardian = enderman.getPassengers().get(0);
                guardian.remove();
                event.getDrops().clear();
                ItemStack stack = new ItemStack(silent_drops.get(plugin.getRandom().nextInt(silent_drops.size())), plugin.getRandom().nextInt(1) + 1);
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
            }
        }
    }
}
