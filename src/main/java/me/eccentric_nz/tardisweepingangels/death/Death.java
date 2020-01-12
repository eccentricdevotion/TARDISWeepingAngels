/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.death;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

/**
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
            angel_drops.add(Material.valueOf(a));
        });
        plugin.getConfig().getStringList("cybermen.drops").forEach((c) -> {
            cyber_drops.add(Material.valueOf(c));
        });
        plugin.getConfig().getStringList("daleks.drops").forEach((d) -> {
            dalek_drops.add(Material.valueOf(d));
        });
        plugin.getConfig().getStringList("empty_child.drops").forEach((e) -> {
            empty_drops.add(Material.valueOf(e));
        });
        plugin.getConfig().getStringList("ice_warriors.drops").forEach((i) -> {
            ice_drops.add(Material.valueOf(i));
        });
        plugin.getConfig().getStringList("sontarans.drops").forEach((o) -> {
            sontaran_drops.add(Material.valueOf(o));
        });
        plugin.getConfig().getStringList("silent.drops").forEach((m) -> {
            silent_drops.add(Material.valueOf(m));
        });
        plugin.getConfig().getStringList("silurians.drops").forEach((s) -> {
            silurian_drops.add(Material.valueOf(s));
        });
        plugin.getConfig().getStringList("vashta_nerada.drops").forEach((v) -> {
            vashta_drops.add(Material.valueOf(v));
        });
        plugin.getConfig().getStringList("zygons.drops").forEach((z) -> {
            zygon_drops.add(Material.valueOf(z));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        PersistentDataContainer pdc = event.getEntity().getPersistentDataContainer();
        if (event.getEntityType().equals(EntityType.SKELETON)) {
            if (pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack stack;
                if (TARDISWeepingAngels.random.nextInt(100) < 3) {
                    stack = new ItemStack(Material.SKELETON_SKULL, 1);
                } else {
                    stack = new ItemStack(angel_drops.get(TARDISWeepingAngels.random.nextInt(angel_drops.size())), TARDISWeepingAngels.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
            }
            if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack stack = new ItemStack(silurian_drops.get(TARDISWeepingAngels.random.nextInt(silurian_drops.size())), TARDISWeepingAngels.random.nextInt(2) + 1);
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
            }
            if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack stack = new ItemStack(dalek_drops.get(TARDISWeepingAngels.random.nextInt(dalek_drops.size())), 1);
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
            }
        }
        if (event.getEntityType().equals(EntityType.PIG_ZOMBIE)) {
            if (pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack stack = new ItemStack(ice_drops.get(TARDISWeepingAngels.random.nextInt(ice_drops.size())), TARDISWeepingAngels.random.nextInt(1) + 1);
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
            }
        }
        if (event.getEntityType().equals(EntityType.ZOMBIE)) {
            ItemStack stack;
            if (pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TARDISWeepingAngels.random.nextInt(100) < 3) {
                    stack = new ItemStack(Material.IRON_INGOT, 1);
                } else {
                    stack = new ItemStack(cyber_drops.get(TARDISWeepingAngels.random.nextInt(cyber_drops.size())), 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
            }
            if (pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TARDISWeepingAngels.random.nextInt(100) < 3) {
                    stack = new ItemStack(Material.POTION, 1, (short) 8197); //TODO use PotionMeta
                } else {
                    stack = new ItemStack(empty_drops.get(TARDISWeepingAngels.random.nextInt(empty_drops.size())), TARDISWeepingAngels.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
            }
            if (pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TARDISWeepingAngels.random.nextInt(100) < 3) {
                    stack = new ItemStack(Material.MILK_BUCKET, 1);
                } else {
                    stack = new ItemStack(sontaran_drops.get(TARDISWeepingAngels.random.nextInt(sontaran_drops.size())), 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
            }
            if (pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                stack = new ItemStack(vashta_drops.get(TARDISWeepingAngels.random.nextInt(vashta_drops.size())), TARDISWeepingAngels.random.nextInt(2) + 1);
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
            }
            if (pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                stack = new ItemStack(zygon_drops.get(TARDISWeepingAngels.random.nextInt(zygon_drops.size())), TARDISWeepingAngels.random.nextInt(1) + 1);
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                return;
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
                PersistentDataContainer apdc = attacker.getPersistentDataContainer();
                if (attacker instanceof Zombie && apdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)) {
                    Location l = event.getEntity().getLocation();
                    LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
                    e.setSilent(true);
                    TARDISWeepingAngels.getEqipper().setCyberEquipment(e, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIE, Monster.CYBERMAN, l));
                    if (event.getEntity() instanceof Player) {
                        String name = event.getEntity().getName();
                        e.setCustomName(name);
                        e.setCustomNameVisible(true);
                    }
                    return;
                }
                if (apdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                    if (event.getEntity() instanceof Player) {
                        Player p = (Player) event.getEntity();
                        plugin.getEmpty().add(p.getUniqueId());
                    }
                }
            }
        }
        if (event.getEntityType().equals(EntityType.ENDERMAN)) {
            if (pdc.has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)) {
                // remove the guardian as well
                Entity guardian = event.getEntity().getPassengers().get(0);
                guardian.remove();
                event.getDrops().clear();
                ItemStack stack = new ItemStack(silent_drops.get(TARDISWeepingAngels.random.nextInt(silent_drops.size())), TARDISWeepingAngels.random.nextInt(1) + 1);
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
            }
        }
    }
}
