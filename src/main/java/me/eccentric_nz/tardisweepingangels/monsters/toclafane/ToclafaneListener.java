package me.eccentric_nz.tardisweepingangels.monsters.toclafane;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ToclafaneListener implements Listener {

    private final TARDISWeepingAngels plugin;
    private final List<Material> drops = new ArrayList<>();

    public ToclafaneListener(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        plugin.getConfig().getStringList("toclafane.drops").forEach((d) -> {
            drops.add(Material.valueOf(d));
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageToclafane(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (entity instanceof ArmorStand && damager instanceof Player) {
            ArmorStand stand = (ArmorStand) event.getEntity();
            Player player = (Player) event.getDamager();
            if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER)) {
                event.setCancelled(true);
                int maxHealth = (stand.getLocation().getWorld().getDifficulty().ordinal() * 6) + 1;
                int health = stand.getPersistentDataContainer().get(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER);
                if (health == maxHealth) {
                    // get the bee and make it angry
                    Bee bee = (Bee) stand.getVehicle();
                    if (bee == null) {
                        bee = (Bee) stand.getLocation().getWorld().spawnEntity(stand.getLocation(), EntityType.BEE);
                        PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
                        bee.addPotionEffect(p);
                        bee.addPassenger(stand);
                    }
                    ItemStack head = stand.getHelmet();
                    ItemMeta im = head.getItemMeta();
                    player.playSound(stand.getLocation(), "toclafane", 1.0f, 1.0f);
                    im.setCustomModelData(2);
                    head.setItemMeta(im);
                    stand.setHelmet(head);
                    bee.setHasStung(false);
                    bee.setHealth(bee.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                    bee.setAnger(500);
                    bee.setTarget(player);
                    bee.setSilent(true);
                    stand.getPersistentDataContainer().set(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER, maxHealth - 1);
                } else {
                    player.playSound(stand.getLocation(), "dalek_hit", 1.0f, 1.0f);
                    health--;
                    if (health == 0) {
                        Location location = stand.getLocation();
                        // kill the toclafane
                        if (stand.getVehicle() != null) {
                            Entity bee = stand.getVehicle();
                            if (bee instanceof Bee) {
                                stand.remove();
                                bee.remove();
                            }
                        } else {
                            stand.remove();
                        }
                        // explode
                        location.getWorld().createExplosion(location, 2.0f, false, true);
                        // give drops
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            ItemStack stack = new ItemStack(drops.get(TARDISWeepingAngels.random.nextInt(drops.size())), TARDISWeepingAngels.random.nextInt(1) + 1);
                            location.getWorld().dropItemNaturally(location, stack);
                        }, 3L);
                    } else {
                        stand.getPersistentDataContainer().set(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER, health);
                    }
                }
            }
        } else if (entity instanceof Bee) {
            if (entity.getPassengers().size() > 0) {
                Entity passenger = entity.getPassengers().get(0);
                if (passenger instanceof ArmorStand && passenger.getPersistentDataContainer().has(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER)) {
                    ((Bee) entity).setHasStung(false);
                }
            }
        } else if (entity instanceof Player && damager instanceof Bee) {
            if (damager.getPassengers().size() > 0) {
                Entity passenger = damager.getPassengers().get(0);
                if (passenger instanceof ArmorStand && passenger.getPersistentDataContainer().has(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER)) {
                    Bee bee = (Bee) damager;
                    bee.setHasStung(false);
                    bee.setHealth(bee.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                    bee.setTarget((Player) entity);
                }
            }
        }
    }

    @EventHandler
    public void onBeeTargetEvent(EntityTargetEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Bee) {
            Bee bee = (Bee) entity;
            if (bee.getTarget() instanceof org.bukkit.entity.Player || bee.getAnger() >= 0) {
                if (bee.getPassengers().size() > 0) {
                    Entity passenger = bee.getPassengers().get(0);
                    if (passenger instanceof ArmorStand && passenger.getPersistentDataContainer().has(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER)) {
                        bee.setHasStung(false);
                    }
                }
            }
        }
    }
}
