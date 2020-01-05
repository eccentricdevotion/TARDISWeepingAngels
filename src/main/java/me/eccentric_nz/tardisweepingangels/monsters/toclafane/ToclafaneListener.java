package me.eccentric_nz.tardisweepingangels.monsters.toclafane;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
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

public class ToclafaneListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageToclafane(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (entity instanceof ArmorStand && damager instanceof Player) {
            ArmorStand stand = (ArmorStand) event.getEntity();
            if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER)) {
                event.setCancelled(true);
                // get the bee and make it angry
                Bee bee = (Bee) stand.getVehicle();
                if (bee == null) {
                    bee = (Bee) stand.getLocation().getWorld().spawnEntity(stand.getLocation(), EntityType.BEE);
                    PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
                    bee.addPotionEffect(p);
                    bee.addPassenger(stand);
                }
                Player player = (Player) event.getDamager();
                ItemStack head = stand.getHelmet();
                ItemMeta im = head.getItemMeta();
                int cmd = im.getCustomModelData();
                int attack = 1;
                int anger = 0;
                LivingEntity living = null;
                if (cmd == 1) {
                    // make the bee angry and target the player
                    attack = 2;
                    anger = 500;
                    living = player;
                }
                player.playSound(stand.getLocation(), "toclafane", 1.0f, 1.0f);
                im.setCustomModelData(attack);
                head.setItemMeta(im);
                stand.setHelmet(head);
                bee.setHasStung(false);
                bee.setHealth(bee.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                bee.setAnger(anger);
                bee.setTarget(living);
                bee.setSilent(true);
            }
        } else if (entity instanceof Bee) {
            if (entity.getPassengers().size() > 0) {
                Entity passenger = entity.getPassengers().get(0);
                if (passenger instanceof ArmorStand && passenger.getPersistentDataContainer().has(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER)) {
                    ((Bee) entity).setHasStung(false);
                    TARDISWeepingAngels.plugin.debug("Set has stung false");
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
